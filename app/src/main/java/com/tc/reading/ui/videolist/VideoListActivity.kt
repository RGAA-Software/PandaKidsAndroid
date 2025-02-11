package com.tc.reading.ui.videolist;

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.SeekParameters
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.tc.android.basic.NetworkRequester
import com.tc.reading.App
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.PkVideo
import com.tc.reading.entity.PkVideoSuit
import com.tc.reading.res.VideoResManager
import com.tc.reading.ui.subtitle.GSYExoSubTitlePlayerManager
import com.tc.reading.ui.subtitle.GSYExoSubTitleVideoView
import com.tc.reading.util.ScreenUtil

@UnstableApi
class VideoListActivity : AppCompatActivity() {

    private val TAG = "VideoList"

    private lateinit var appCtx: AppContext
    private lateinit var videoResManager: VideoResManager
    private lateinit var videoListAdapter: VideoListAdapter
    private var mainVideos = mutableListOf<PkVideo>()
    private lateinit var detailPlayer: GSYExoSubTitleVideoView
    private lateinit var orientationUtils: OrientationUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScreenUtil.makeActivityFullScreen(this)
        setContentView(R.layout.activity_video_list)

        appCtx = (application as App).getAppContext()
        videoResManager = appCtx.getVideoResManager()
        val videoSuit = intent.getSerializableExtra("videoSuit") as PkVideoSuit
        Log.i(TAG, "VideoList: $videoSuit")

        val videoList = findViewById<RecyclerView>(R.id.video_list)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        videoList.layoutManager = layoutManager
        videoList.addItemDecoration(VideoListItemDecoration())
        videoListAdapter = VideoListAdapter(appCtx, mainVideos)
        videoList.adapter = videoListAdapter
        videoListAdapter.onVideoClickListener = object: VideoListAdapter.OnVideoClickListener {
            override fun onVideoClicked(video: PkVideo) {
                Log.i(TAG, "video ==> $video")
                val url = appCtx.getBaseServerUrl() + "/" + video.file
                val funStartPlaying = {
                    detailPlayer.setUp(url, true, video.name)
                    detailPlayer.startPlayLogic()
                }

                if (!TextUtils.isEmpty(video.subtitlePath)) {
                    val subtitleUrl = appCtx.getBaseServerUrl() + "/" + video.subtitlePath
                    appCtx.postBgTask {
                        if (NetworkRequester.checkRemoteFileExists(subtitleUrl)) {
                            detailPlayer.subTitle = subtitleUrl
                            Log.i(TAG, "subtitle exist: $subtitleUrl")
                        }
                        appCtx.postUITask {
                            funStartPlaying()
                        }
                    }

                } else {
                    funStartPlaying()
                }
            }
        }

        detailPlayer = findViewById(R.id.detail_player)
        orientationUtils = OrientationUtils(this, detailPlayer);
        orientationUtils.setEnable(false);

        val gsyVideoOption = GSYVideoOptionBuilder();
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.mipmap.ic_launcher)

        gsyVideoOption
            .setThumbImageView(imageView)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setAutoFullWithSize(false)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            //.setUrl(url)
            .setCacheWithPlay(false)
            .setVideoTitle("")
            .setVideoAllCallBack(object: GSYSampleCallBack() {
                override fun onPrepared(url: String?, vararg objects: Any?) {
                    super.onPrepared(url, *objects)
                    if (detailPlayer.getGSYVideoManager()?.player is GSYExoSubTitlePlayerManager) {
                        (detailPlayer.getGSYVideoManager()?.player as GSYExoSubTitlePlayerManager).setSeekParameter(SeekParameters.NEXT_SYNC)
                        Debuger.printfError("***** setSeekParameter **** ")
                    }
                }
            }).setLockClickListener { view, lock ->
                orientationUtils.setEnable(!lock)
            }.build(detailPlayer);

        detailPlayer.fullscreenButton.setOnClickListener { //直接横屏
            //orientationUtils.resolveByClick();
            detailPlayer.startWindowFullscreen(this@VideoListActivity, false, false);
        };

        videoResManager.queryVideos(1, 200, videoSuit.id) {videos ->
            if (videos == null || videos.size <= 0) {
                Log.e(TAG, "query video for $videoSuit failed!")
                return@queryVideos
            }

            videos.forEach {
                Log.i(TAG, "video: " + it.name + ", cover: " + it.cover)
            }
            mainVideos.removeAll(videos)
            mainVideos.addAll(videos)
            appCtx.postUITask {
                videoListAdapter.notifyDataSetChanged()
            }
        }

    }

    override fun onPause() {
        super.onPause()
        detailPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        detailPlayer.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        detailPlayer.release()
    }

    override fun onBackPressed() {
        if (detailPlayer.isIfCurrentIsFullscreen) {
            detailPlayer.onBackFullscreen()
            return
        }
        super.onBackPressed()
    }
}
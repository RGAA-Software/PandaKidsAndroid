package com.tc.reading.ui.video;

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.SeekParameters
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.tc.reading.App
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.PkVideo
import com.tc.reading.entity.PkVideoSuit
import com.tc.reading.res.VideoResManager
import com.tc.reading.ui.video.subtitle.GSYExoSubTitlePlayerManager
import com.tc.reading.ui.video.subtitle.GSYExoSubTitleVideoView
import com.tc.reading.util.ScreenUtil


@UnstableApi
class VideoListActivity : AppCompatActivity() {

    private val TAG = "VideoList"

    private lateinit var appCtx: AppContext
    private lateinit var videoResManager: VideoResManager
    private lateinit var videoListAdapter: VideoListAdapter
    private var mainVideos = mutableListOf<PkVideo>()
    //private lateinit var exoPlayer: ExoPlayer
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
//                if (exoPlayer.isPlaying) {
//
//                }
                val url = appCtx.getBaseServerUrl() + "/" + video.file
//                exoPlayer.setMediaItem(MediaItem.fromUri(url))

                detailPlayer.setUp(url, true, video.name)
                //detailPlayer.subTitle = "http://192.168.31.5:9988/Resources/Preset/035%20%E5%B0%8F%E7%8B%90%E7%8B%B8%2001-09/level03%EF%BC%8810%E9%83%A8%EF%BC%89/08%20The%20Wind%20in%20the%20Willows/001_The%20Wind%20in%20the%20Willows%201_It%20Is%20Spring!.srt"
                //detailPlayer.subTitle = appCtx.getBaseServerUrl() + "/Resources/Preset/035 小狐狸 01-09/level03（10部）/08 The Wind in the Willows/001_The Wind in the Willows 1_It Is Spring!.srt"
                detailPlayer.startPlayLogic();
            }
        }

//        val playerView: PlayerView = findViewById(R.id.video_view);
//        exoPlayer = ExoPlayer.Builder(this).build()
//        playerView.player = exoPlayer
//        exoPlayer.prepare()
//        exoPlayer.playWhenReady = true

        detailPlayer = findViewById(R.id.detail_player)
        ///外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, detailPlayer);
//初始化不打开外部的旋转
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


                    //设置 seek 的临近帧。
                    if (detailPlayer.getGSYVideoManager()
                            .getPlayer() is GSYExoSubTitlePlayerManager
                    ) {
                        (detailPlayer.getGSYVideoManager()
                            .getPlayer() as GSYExoSubTitlePlayerManager).setSeekParameter(
                            SeekParameters.NEXT_SYNC
                        )
                        Debuger.printfError("***** setSeekParameter **** ")
                    }


                    ///TODO 注意，用这个 M3U8 的话，内部会有内嵌字幕 embedded caption
                    ///TODO 所以就算你加了外挂字幕，也需要再切换一次才能看到外部字幕
                    ///TODO 这里输出所有字幕信息
//                    if (detailPlayer.getGSYVideoManager()
//                            .getPlayer() is GSYExoSubTitlePlayerManager
//                    ) {
//                        val mappedTrackInfo = detailPlayer.getGSYVideoManager().player.mediaPlayer.trackSelector.currentMappedTrackInfo
////                        val mappedTrackInfo = detailPlayer.getGSYVideoManager().player.mediaPlayer.trackInfo
//                        if (mappedTrackInfo != null) {
//                            for (i in 0 until mappedTrackInfo.rendererCount) {
//                                val rendererTrackGroups = mappedTrackInfo.getTrackGroups(i)
//                                if (C.TRACK_TYPE_TEXT == mappedTrackInfo.getRendererType(i)) { //判断是否是音轨
//                                    for (j in 0 until rendererTrackGroups.length) {
//                                        val trackGroup = rendererTrackGroups[j]
//                                        Debuger.printfError(
//                                            "####### Text " + trackGroup.getFormat(0)
//                                                .toString() + " #######"
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    }


                }
            }).setLockClickListener(object: LockClickListener {
                override fun onClick(view: View?, lock: Boolean) {
                    if (orientationUtils != null) {
                        orientationUtils.setEnable(!lock);
                    }
                }
            }).build(detailPlayer);

        detailPlayer.getFullscreenButton().setOnClickListener(object: OnClickListener {
            override fun onClick(v: View?) {
                //直接横屏
                //orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(this@VideoListActivity, false, false);
            }
        });


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
//        exoPlayer.pause()
        if (detailPlayer != null) {
            detailPlayer.onVideoPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (detailPlayer != null) {
            detailPlayer.onVideoResume()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        exoPlayer.release()
        if (detailPlayer != null) {
            detailPlayer.release()
        }
    }
}
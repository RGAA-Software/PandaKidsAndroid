package com.tc.reading.ui.video;

import android.os.Bundle;
import android.util.Log
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tc.reading.App
import com.tc.reading.AppContext

import com.tc.reading.R;
import com.tc.reading.entity.PkVideo
import com.tc.reading.entity.PkVideoSuit
import com.tc.reading.res.VideoResManager
import com.tc.reading.util.ScreenUtil

class VideoListActivity : AppCompatActivity() {

    private val TAG = "VideoList"

    private lateinit var appCtx: AppContext
    private lateinit var videoResManager: VideoResManager
    private lateinit var videoListAdapter: VideoListAdapter
    private var mainVideos = mutableListOf<PkVideo>()
    private lateinit var exoPlayer: ExoPlayer

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
                if (exoPlayer.isPlaying) {

                }
                val url = appCtx.getBaseServerUrl() + "/" + video.file
                exoPlayer.setMediaItem(MediaItem.fromUri(url))
            }
        }

        val playerView: PlayerView = findViewById(R.id.video_view);
        exoPlayer = ExoPlayer.Builder(this).build()
        playerView.player = exoPlayer
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

        videoResManager.queryVideos(1, 200, videoSuit.id) {videos ->
            if (videos == null || videos.size <= 0) {
                Log.e(TAG, "query video for $videoSuit failed!")
                return@queryVideos
            }

            videos.forEach {
                Log.i(TAG, "video: " + it.name)
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
        exoPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }
}
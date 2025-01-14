package com.tc.reading.ui.video;

import android.os.Bundle;
import android.util.Log
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.tc.reading.App
import com.tc.reading.AppContext

import com.tc.reading.R;
import com.tc.reading.entity.VideoSuit
import com.tc.reading.res.VideoResManager

class VideoListActivity : AppCompatActivity() {

    private val TAG = "VideoList"

    private lateinit var appCtx: AppContext
    private lateinit var videoResManager: VideoResManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)

        appCtx = (application as App).getAppContext()
        videoResManager = appCtx.getVideoResManager()
        val videoSuit = intent.getSerializableExtra("videoSuit") as VideoSuit
        Log.i(TAG, "VideoList: $videoSuit")

        videoResManager.queryVideos(1, 2, videoSuit.id) {r, videos ->
            videos?.forEach {
                Log.i(TAG, "video: " + it.name)   
            }
        }

    }
}
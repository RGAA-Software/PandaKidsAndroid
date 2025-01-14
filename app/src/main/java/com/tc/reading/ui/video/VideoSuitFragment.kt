package com.tc.reading.ui.video

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simform.refresh.SSPullToRefreshLayout
import com.tc.reading.App
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.VideoSuit
import com.tc.reading.res.VideoResManager
import com.tc.reading.ui.BaseFragment


class VideoSuitFragment() : BaseFragment() {
    private val TAG = "VideoFragment";
    private lateinit var refreshLayout: SSPullToRefreshLayout
    private lateinit var videoResManager: VideoResManager;
    private var mainVideoSuits: MutableList<VideoSuit> = mutableListOf()
    private var recommendVideoSuit: MutableList<VideoSuit> = mutableListOf()
    private lateinit var videoSuitAdapter: VideoSuitAdapter
    private lateinit var appCtx: AppContext

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoResManager = appContext.getVideoResManager()
        appCtx = (requireActivity().application as App).getAppContext()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_video, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshLayout = view.findViewById(R.id.refresh_layout)
        refreshLayout.apply {
            setRepeatMode(SSPullToRefreshLayout.RepeatMode.REPEAT);
            setRepeatCount(SSPullToRefreshLayout.RepeatCount.INFINITE);
            setRefreshStyle(SSPullToRefreshLayout.RefreshStyle.NORMAL);
            setLottieAnimation("lottie_clock.json");
            setOnRefreshListener {
                handler.postDelayed({
                    setRefreshing(false);
                }, 2000)
            }
        }

        val videoList = view.findViewById<RecyclerView>(R.id.video_list)
        val layoutManager = GridLayoutManager(context, 4)
        videoList.addItemDecoration(VideoSuitItemDecoration())
        videoList.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 4 else 1
            }
        }

        videoSuitAdapter = VideoSuitAdapter(appCtx, mainVideoSuits, recommendVideoSuit)
        videoList.adapter = videoSuitAdapter
        videoSuitAdapter.onVideoSuitClickListener = object: VideoSuitAdapter.OnVideoSuitClickListener {
            override fun onVideoSuitClicked(vs: VideoSuit) {
                Log.i(TAG, "==> $vs")
                val intent = Intent(requireActivity(), VideoListActivity::class.java)
                intent.putExtra("videoSuit", vs)
                startActivity(intent)
            }
        }

        requestRecommendSuits()
        requestVideoSuits()
    }

    private fun setupViewPager() {

    }

    override fun onResume() {
        super.onResume()
        videoSuitAdapter.onResume()

    }

    override fun onPause() {
        super.onPause()
        videoSuitAdapter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoSuitAdapter.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun requestRecommendSuits() {
        videoResManager.getTodayVideoSuits { r, vs ->
            if (!r || vs == null) {
                return@getTodayVideoSuits;
            }
            for (suit in vs) {
                Log.i(TAG, "" + suit)
            }
            recommendVideoSuit.removeAll(vs)
            recommendVideoSuit.addAll(vs)
            appContext.postUITask {
                videoSuitAdapter.refreshBanner()
            }
        }
    }

    private fun requestVideoSuits() {
        videoResManager.queryVideoSuits(1, 10) { r, vs ->
            if (!r || vs == null) {
                return@queryVideoSuits;
            }
            for (suit in vs) {
                Log.i(TAG, "" + suit)
            }
            mainVideoSuits.removeAll(vs)
            mainVideoSuits.addAll(vs)
            appContext.postUITask {
                videoSuitAdapter.notifyDataSetChanged()
            }
        }
    }
}
package com.tc.reading.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simform.refresh.SSPullToRefreshLayout
import com.tc.reading.R
import com.tc.reading.ui.BaseFragment


class VideoFragment() : BaseFragment() {

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var refreshLayout: SSPullToRefreshLayout

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exoPlayer = activity?.let { ExoPlayer.Builder(it).build() }!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_video, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playerView: PlayerView = view.findViewById(R.id.video_view);
        playerView.player = exoPlayer
        exoPlayer.addMediaItem(MediaItem.fromUri("http://192.168.31.5:9988/pandakids/stream/videos/week6.mp4"))
//        exoPlayer.addMediaItem(MediaItem.fromUri("http://192.168.31.5:9988/resources/videos/week6.mp4"))
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

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
        videoList.addItemDecoration(VideoItemDecoration())
        videoList.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 4 else 1
            }
        }
        videoList.adapter = VideoAdapter()

    }

    private fun setupViewPager() {
    }


    override fun onResume() {
        super.onResume()
//        if (mViewPager != null)
//            mViewPager.startLoop();
    }

    override fun onPause() {
//        if (mViewPager != null)
//            mViewPager.stopLoop();
        super.onPause()
        exoPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()

//        if (mViewPager != null)
//            mViewPager.stopLoop();
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}
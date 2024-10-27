package com.tc.reading.ui.video

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.tc.reading.R
import com.tc.reading.databinding.FragmentVideoBinding
import com.tc.reading.ui.BaseFragment

class VideoFragment() : BaseFragment() {

    private lateinit var exoPlayer: ExoPlayer

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
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}
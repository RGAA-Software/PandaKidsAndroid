package com.tc.reading.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tc.reading.AppContext
import com.tc.reading.databinding.FragmentVideoBinding
import com.tc.reading.ui.BaseFragment

class VideoFragment() : BaseFragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dashboardViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testVideoPlayer.setIsTouchWigetFull(true);
        binding.testVideoPlayer.setUp("https://media.w3.org/2010/05/sintel/trailer.mp4", true, "Test...");
        binding.testButton.setOnClickListener {
            binding.testButton.setText("GGG")
        }
    }

    override fun onResume() {
        super.onResume()
        if (!binding.testVideoPlayer.isInPlayingState) {
            binding.testVideoPlayer.onVideoResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (binding.testVideoPlayer.isInPlayingState) {
            binding.testVideoPlayer.onVideoPause()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
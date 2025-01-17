package com.tc.reading.ui.audio

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simform.refresh.SSPullToRefreshLayout
import com.tc.reading.R
import com.tc.reading.entity.PkAudioSuit
import com.tc.reading.ui.BaseFragment

class AudioSuitFragment() : BaseFragment() {
    private val TAG = "AudioSuitFragment"

    private var handler: Handler? = null
    private var audioSuitAdapter: AudioSuitAdapter? = null
    private var mainAudioSuits = mutableListOf<PkAudioSuit>()
    private lateinit var audioSuitList: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.handler = Handler(Looper.getMainLooper());
        val root = View.inflate(requireContext(), R.layout.fragment_audio_suit, null)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        audioSuitList = view.findViewById(R.id.id_audio_suit)
        val refreshLayout = view.findViewById<SSPullToRefreshLayout>(R.id.refresh_layout)
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

        audioSuitAdapter = AudioSuitAdapter(appContext, requireActivity(), mainAudioSuits)
        audioSuitAdapter!!.onItemClickListener = object: AudioSuitAdapter.OnItemClickListener {
            override fun onItemClicked(audioSuit: PkAudioSuit) {
                val intent = Intent(activity, AudioListActivity::class.java)
                intent.putExtra("audioSuit", audioSuit)
                requireActivity().startActivity(intent)
            }
        }

        audioSuitList.apply {
            layoutManager = GridLayoutManager(activity, 4)
            (layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == 0) 4 else 1
                }
            }
            adapter = audioSuitAdapter
            addItemDecoration(AudioSuitItemDecoration());
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val manager = recyclerView.layoutManager as GridLayoutManager;
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                        if (lastVisibleItem == (mainAudioSuits.size - 1)) {
                            Toast.makeText(activity, "Last...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        appContext.getAudioResManager().queryAudioSuits(1, 20) { audioSuits ->
            if (audioSuits == null) {
                return@queryAudioSuits
            }
            audioSuits.forEach {
                Log.i(TAG, "bookSuit: $it")
            }

            mainAudioSuits.removeAll(audioSuits)
            mainAudioSuits.addAll(audioSuits)
            appContext.postUITask {
                audioSuitAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
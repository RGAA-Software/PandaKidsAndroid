package com.tc.reading.ui.cartoon

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simform.refresh.SSPullToRefreshLayout
import com.tc.reading.R
import com.tc.reading.entity.PkVideoSuit
import com.tc.reading.res.VideoResManager
import com.tc.reading.ui.BaseFragment
import com.tc.reading.ui.videolist.VideoListActivity


class CartoonSuitFragment() : BaseFragment() {
    private val TAG = "VideoFragment";
    private var refreshLayout: SSPullToRefreshLayout? = null
    private var videoResManager: VideoResManager? = null
    private var mainVideoSuits: MutableList<PkVideoSuit> = mutableListOf()
    private var recommendVideoSuit: MutableList<PkVideoSuit> = mutableListOf()
    private var cartoonSuitAdapter: CartoonSuitAdapter? = null

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoResManager = appContext.getVideoResManager()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_video, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshLayout = view.findViewById(R.id.refresh_layout)
        refreshLayout?.apply {
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
        videoList.addItemDecoration(CartoonSuitItemDecoration())
        videoList.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 4 else 1
            }
        }

        cartoonSuitAdapter = CartoonSuitAdapter(appContext, mainVideoSuits, recommendVideoSuit)
        videoList.adapter = cartoonSuitAdapter
        cartoonSuitAdapter?.onCartoonSuitClickListener = object: CartoonSuitAdapter.OnCartoonSuitClickListener {
            override fun onVideoSuitClicked(vs: PkVideoSuit) {
                Log.i(TAG, "==> $vs")
                val intent = Intent(requireActivity(), VideoListActivity::class.java)
                intent.putExtra("videoSuit", vs)
                startActivity(intent)
            }
        }

        //requestRecommendSuits()
        requestVideoSuits()
    }

    private fun setupViewPager() {

    }

    override fun onResume() {
        super.onResume()
        cartoonSuitAdapter?.onResume()

    }

    override fun onPause() {
        super.onPause()
        cartoonSuitAdapter?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        cartoonSuitAdapter?.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun requestRecommendSuits() {
        videoResManager?.getTodayVideoSuits { vs ->
            if (vs == null || vs.size <= 0) {
                return@getTodayVideoSuits;
            }
            for (suit in vs) {
                Log.i(TAG, "" + suit)
            }
            recommendVideoSuit.removeAll(vs)
            recommendVideoSuit.addAll(vs)
            appContext.postUITask {
                cartoonSuitAdapter?.refreshBanner()
            }
        }
    }

    private fun requestVideoSuits() {
        videoResManager?.queryVideoSuits(1, 20) { vs ->
            if (vs == null || vs.size <= 0) {
                return@queryVideoSuits;
            }
            for (suit in vs) {
                Log.i(TAG, "" + suit)
            }
            mainVideoSuits.removeAll(vs)
            mainVideoSuits.addAll(vs)
            appContext.postUITask {
                cartoonSuitAdapter?.myNotifyDataSetChanged()
            }
        }
    }
}
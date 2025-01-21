package com.tc.reading.ui.documentary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simform.refresh.SSPullToRefreshLayout
import com.tc.reading.R
import com.tc.reading.entity.PkVideoSuit
import com.tc.reading.res.VideoResManager
import com.tc.reading.ui.BaseFragment
import com.tc.reading.ui.cartoon.CartoonSuitItemDecoration

class DocumentaryFragment : BaseFragment() {
    private val TAG = "Documentary"

    private var refreshLayout: SSPullToRefreshLayout? = null
    private var videoResManager: VideoResManager? = null
    private var documentarySuits: MutableList<PkVideoSuit> = mutableListOf()
    private var documentarySuitAdapter: DocumentarySuitAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoResManager = appContext.getVideoResManager()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(requireContext(), R.layout.fragment_documentary, null)
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

        val videoList = view.findViewById<RecyclerView>(R.id.id_documentary_list)
        val layoutManager = GridLayoutManager(context, 4)
        videoList.addItemDecoration(CartoonSuitItemDecoration())
        videoList.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 4 else 1
            }
        }

        documentarySuitAdapter = DocumentarySuitAdapter(appContext, documentarySuits)
        videoList.adapter = documentarySuitAdapter
        documentarySuitAdapter?.onDocumentarySuitClickListener = object: DocumentarySuitAdapter.OnDocumentarySuitClickListener {
            override fun onVideoSuitClicked(vs: PkVideoSuit) {
                Log.i(TAG, "==> $vs")
//                val intent = Intent(requireActivity(), VideoListActivity::class.java)
//                intent.putExtra("videoSuit", vs)
//                startActivity(intent)
            }
        }

        queryDocumentaries()
    }

    private fun queryDocumentaries() {
        for (i in 0..29) {
            val videoSuit = PkVideoSuit()
            videoSuit.name = "Documentary: $i"
            documentarySuits.add(videoSuit)
        }
        documentarySuitAdapter?.notifyDataSetChanged()
    }

}
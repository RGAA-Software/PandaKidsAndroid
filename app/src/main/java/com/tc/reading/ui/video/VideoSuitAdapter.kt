package com.tc.reading.ui.video

import android.content.res.Resources
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.PkVideoSuit
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.PageStyle
import com.zhpan.indicator.enums.IndicatorStyle

class VideoSuitAdapter(private var appCtx: AppContext,
                       private var mainVideoSuits: MutableList<PkVideoSuit>,
                       private var recommendVideoSuit: MutableList<PkVideoSuit>)
    : RecyclerView.Adapter<VideoSuitAdapter.VideoHolder>() {
    private val TAG = "VideoAdapter"
    private var viewPager: BannerViewPager<PkVideoSuit>? = null
    var onVideoSuitClickListener: OnVideoSuitClickListener? = null
    private var videoLabelAdapter: VideoLabelAdapter? = null
    private var videoLabelView: View? = null

    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnVideoSuitClickListener {
        fun onVideoSuitClicked(vs: PkVideoSuit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view: View
        if (viewType == 0) {
            if (videoLabelView == null) {
                view = View.inflate(parent.context, R.layout.item_video_suit_filter, null)
                videoLabelView = view
                val labelsView = view.findViewById<RecyclerView>(R.id.id_labels)
                val mockData = mutableListOf<VideoLabel>()
                for (i in 0..20) {
                    mockData.add(VideoLabel())
                }
                videoLabelAdapter = VideoLabelAdapter(appCtx, mockData)
                labelsView.adapter = videoLabelAdapter
                var flexMgr = FlexboxLayoutManager(view.context, FlexDirection.ROW)
                flexMgr.justifyContent = JustifyContent.FLEX_START
                flexMgr.alignItems = AlignItems.CENTER
                labelsView.layoutManager = flexMgr
                videoLabelAdapter!!.notifyDataSetChanged()
            } else {
                view = videoLabelView!!
            }

        } else {
            view = View.inflate(parent.context, R.layout.item_video_suits, null)
        }
        return VideoHolder(view)
    }

    fun refreshBanner() {
        viewPager?.refreshData(recommendVideoSuit);
    }

    override fun getItemCount(): Int {
        return mainVideoSuits.size + 1;
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 0
        }
        return 1
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        if (position == 0) {
            return
        }

        // click
        holder.itemView.setOnClickListener {
            if (onVideoSuitClickListener != null) {
                val vs = mainVideoSuits[position - 1]
                onVideoSuitClickListener!!.onVideoSuitClicked(vs)
            }
        }

        val suit = mainVideoSuits.get(position - 1)
        val titleView = holder.itemView.findViewById<TextView>(R.id.id_title)
        titleView.text = suit.name

        val subTitleView = holder.itemView.findViewById<TextView>(R.id.id_subtitle)
        if (!TextUtils.isEmpty(suit.summary)) {
            subTitleView.text = suit.summary
        }
        else {
            subTitleView.text = "~~"
        }

        val coverView = holder.itemView.findViewById<ImageView>(R.id.id_cover)
        val coverUrl = appCtx.getBaseServerUrl() + "/" + suit.cover
        Log.i(TAG, "coverUrl: $coverUrl")
        Glide.with(coverView).load(coverUrl).into(coverView)
    }

    fun onResume() {
        //mViewPager?.startLoop();
    }

    fun onPause() {
        //mViewPager?.stopLoop();
    }

    fun onDestroy() {
        //mViewPager?.stopLoop();
    }

}
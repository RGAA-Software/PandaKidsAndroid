package com.tc.reading.ui.science

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
import com.tc.reading.ui.labelfilter.LabelFilterItem
import com.tc.reading.ui.labelfilter.LabelFilterAdapter

class ScienceSuitAdapter(private var appCtx: AppContext,
                         private var movieSuits: MutableList<PkVideoSuit>)
    : RecyclerView.Adapter<ScienceSuitAdapter.VideoHolder>() {
    private val TAG = "DocumentarySuit"
    var onMovieSuitClickListener: OnMovieSuitClickListener? = null
    private var labelFilterAdapter: LabelFilterAdapter? = null
    private var labeFilterlView: View? = null

    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnMovieSuitClickListener {
        fun onVideoSuitClicked(vs: PkVideoSuit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view: View
        if (viewType == 0) {
            if (labeFilterlView == null) {
                view = View.inflate(parent.context, R.layout.item_suit_label_filter, null)
                labeFilterlView = view
                val labelsView = view.findViewById<RecyclerView>(R.id.id_labels)
                val mockData = mutableListOf<LabelFilterItem>()
                for (i in 0..20) {
                    mockData.add(LabelFilterItem())
                }
                labelFilterAdapter = LabelFilterAdapter(appCtx, mockData)
                labelsView.adapter = labelFilterAdapter
                var flexMgr = FlexboxLayoutManager(view.context, FlexDirection.ROW)
                flexMgr.justifyContent = JustifyContent.FLEX_START
                flexMgr.alignItems = AlignItems.CENTER
                labelsView.layoutManager = flexMgr
                labelFilterAdapter!!.notifyDataSetChanged()
            } else {
                view = labeFilterlView!!
            }

        } else {
            view = View.inflate(parent.context, R.layout.item_video_suits, null)
        }
        return VideoHolder(view)
    }

    override fun getItemCount(): Int {
        return movieSuits.size + 1;
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
            if (onMovieSuitClickListener != null) {
                val vs = movieSuits[position - 1]
                onMovieSuitClickListener!!.onVideoSuitClicked(vs)
            }
        }

        val suit = movieSuits.get(position - 1)
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
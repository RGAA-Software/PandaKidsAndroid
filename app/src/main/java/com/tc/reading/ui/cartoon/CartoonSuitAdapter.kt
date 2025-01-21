package com.tc.reading.ui.cartoon

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
import com.zhpan.bannerview.BannerViewPager

class CartoonSuitAdapter(private var appCtx: AppContext,
                         private var cartoonSuits: MutableList<PkVideoSuit>,
                         private var recommendVideoSuit: MutableList<PkVideoSuit>)
    : RecyclerView.Adapter<CartoonSuitAdapter.VideoHolder>() {
    private val TAG = "VideoAdapter"
    private var viewPager: BannerViewPager<PkVideoSuit>? = null
    var onCartoonSuitClickListener: OnCartoonSuitClickListener? = null
    private var labelFilterAdapter: LabelFilterAdapter? = null
    private var labelFilterView: View? = null
    private var labelListView: RecyclerView? = null
    private var cartoonCategories: MutableList<LabelFilterItem> = mutableListOf()

    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnCartoonSuitClickListener {
        fun onVideoSuitClicked(vs: PkVideoSuit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view: View
        if (viewType == 0) {
            if (labelFilterView == null) {
                view = View.inflate(parent.context, R.layout.item_suit_label_filter, null)
                val expandLabelView = view.findViewById<ImageView>(R.id.id_expand_labels)
                expandLabelView.setImageResource(R.drawable.ic_arrow_up)
                expandLabelView.setOnClickListener {
                    labelFilterAdapter!!.notifyDataSetChanged()
                    if (labelListView!!.visibility == View.VISIBLE) {
                        labelListView!!.visibility = View.GONE
                        expandLabelView.setImageResource(R.drawable.ic_arrow_up)
                    } else {
                        labelListView!!.visibility = View.VISIBLE
                        expandLabelView.setImageResource(R.drawable.ic_arrow_down)
                    }
                }

                labelFilterView = view
                labelListView = view.findViewById<RecyclerView>(R.id.id_labels)
                labelFilterAdapter = LabelFilterAdapter(appCtx, cartoonCategories)
                labelListView!!.adapter = labelFilterAdapter
                var flexMgr = FlexboxLayoutManager(view.context, FlexDirection.ROW)
                flexMgr.justifyContent = JustifyContent.FLEX_START
                flexMgr.alignItems = AlignItems.CENTER
                labelListView!!.layoutManager = flexMgr
                labelFilterAdapter!!.notifyDataSetChanged()
            } else {
                view = labelFilterView!!
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
        return cartoonSuits.size + 1;
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
            if (onCartoonSuitClickListener != null) {
                val vs = cartoonSuits[position - 1]
                onCartoonSuitClickListener!!.onVideoSuitClicked(vs)
            }
        }

        val suit = cartoonSuits.get(position - 1)
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

    fun myNotifyDataSetChanged() {
        this.notifyDataSetChanged()
        if (labelFilterAdapter != null) {
            cartoonSuits.forEach { suit ->
                suit.categories.forEach { item ->
                    var filterItem = LabelFilterItem(item)
                    if (!cartoonCategories.contains(filterItem)) {
                        cartoonCategories.add(filterItem)
                    }
                }
            }
            labelFilterAdapter!!.notifyDataSetChanged()
        }
    }

}
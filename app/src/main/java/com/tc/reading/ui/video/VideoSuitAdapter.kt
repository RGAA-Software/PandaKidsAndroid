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
    public var onVideoSuitClickListener: OnVideoSuitClickListener? = null

    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnVideoSuitClickListener {
        fun onVideoSuitClicked(vs: PkVideoSuit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view: View
        if (viewType == 0) {
            view = View.inflate(parent.context, R.layout.item_video_suit_filter, null)
            // banner
//            view = View.inflate(parent.context, R.layout.layout_banner, null)
//
//            val bannerViewPager = view.findViewById<BannerViewPager<PkVideoSuit>>(R.id.banner_view)!!
//            val density = Resources.getSystem().displayMetrics.density
//            viewPager = bannerViewPager
//            //mViewPager.setPageStyle(PageStyle.MULTI_PAGE_SCALE)
//            bannerViewPager.setPageStyle(PageStyle.MULTI_PAGE)
//            bannerViewPager.setPageMargin(15)
//            bannerViewPager.setIndicatorStyle(IndicatorStyle.ROUND_RECT)
//            bannerViewPager.setIndicatorHeight((density * 7).toInt())
//            bannerViewPager.setIndicatorSliderWidth((density * 20).toInt())
//            bannerViewPager.setIndicatorSliderColor(appCtx.getColor(R.color.white), appCtx.getColor(com.rajat.pdfviewer.R.color.colorPrimary))
//            val scWidth = Resources.getSystem().displayMetrics.widthPixels
//            if (scWidth <= 1280) {
//                bannerViewPager.setRevealWidth((density * 280).toInt())
//            }
//            else if (scWidth <= 1920) {
//                bannerViewPager.setRevealWidth((density * 400).toInt())
//            }
//            else if (scWidth <= 2560) {
//                bannerViewPager.setRevealWidth((density * 500).toInt())
//            }
//            else if (scWidth <= 3840) {
//                bannerViewPager.setRevealWidth((density * 500).toInt())
//            }
//            bannerViewPager.setInterval(5000)
//            bannerViewPager.apply {
//                adapter = VideoBannerAdapter(appCtx)
//                //setLifecycleRegistry(lifecycle)
//            }.create()
//
//            bannerViewPager.addData(recommendVideoSuit);
        } else {
            view = View.inflate(parent.context, R.layout.item_video_suits, null)
        }
        return VideoHolder(view);
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
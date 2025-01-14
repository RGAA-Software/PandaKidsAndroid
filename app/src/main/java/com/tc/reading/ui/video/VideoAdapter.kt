package com.tc.reading.ui.video

import android.content.res.Resources
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.VideoSuit
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.PageStyle

class VideoAdapter(private var appCtx: AppContext,
                   private var mainVideoSuits: MutableList<VideoSuit>,
                   private var recommendVideoSuit: MutableList<VideoSuit>)
    : RecyclerView.Adapter<VideoAdapter.VideoHolder>() {
    private val TAG = "VideoAdapter"
    private var mViewPager: BannerViewPager<VideoSuit>? = null


    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view: View
        if (viewType == 0) {
            // banner
            view = View.inflate(parent.context, R.layout.layout_banner, null)
//            view = LayoutInflater.from(parent.context).inflate(R.layout.layout_banner, parent, false);

            val bannerViewPager = view.findViewById<BannerViewPager<VideoSuit>>(R.id.banner_view)!!
            mViewPager = bannerViewPager
            //mViewPager.setPageStyle(PageStyle.MULTI_PAGE_SCALE)
            bannerViewPager.setPageStyle(PageStyle.MULTI_PAGE)
            bannerViewPager.setPageMargin(15)
            bannerViewPager.setRevealWidth((Resources.getSystem().displayMetrics.density * 280).toInt())
            bannerViewPager.setInterval(5000)
            bannerViewPager.apply {
                adapter = VideoBannerAdapter(appCtx)
                //setLifecycleRegistry(lifecycle)
            }.create()

            bannerViewPager.addData(recommendVideoSuit);
        } else {
            view = View.inflate(parent.context, R.layout.item_video, null)
//            view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false);
        }
        return VideoHolder(view);
    }

    fun refreshBanner() {
        mViewPager?.refreshData(recommendVideoSuit);
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
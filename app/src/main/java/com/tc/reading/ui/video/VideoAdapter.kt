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
import com.tc.reading.entity.Video
import com.tc.reading.entity.VideoSuit
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.PageStyle

class VideoAdapter(private var appCtx: AppContext,
                   private var mainVideoSuits: MutableList<VideoSuit>,
                   private var recommendVideoSuit: MutableList<VideoSuit>)
    : RecyclerView.Adapter<VideoAdapter.VideoHolder>() {
    private val TAG = "VideoAdapter"
    private lateinit var mViewPager: BannerViewPager<VideoSuit>


    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        var view: View
        if (viewType == 0) {
            // banner
            view = View.inflate(parent.context, R.layout.layout_banner, null)
            mViewPager = view.findViewById(R.id.banner_view)
            mViewPager.setPageStyle(PageStyle.MULTI_PAGE)
            mViewPager.setPageMargin(20)
            mViewPager.setRevealWidth((Resources.getSystem().displayMetrics.density * 400).toInt())
            mViewPager.setInterval(5000)
            mViewPager.apply {
                adapter = SimpleAdapter()
                //setLifecycleRegistry(lifecycle)
            }.create()

            mViewPager.refreshData(recommendVideoSuit);
        } else {
            view = View.inflate(parent.context, R.layout.item_video, null)
        }
        return VideoHolder(view);
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
        var suit = mainVideoSuits.get(position - 1)
        var titleView = holder.itemView.findViewById<TextView>(R.id.id_title)
        titleView.text = suit.name

        var subTitleView = holder.itemView.findViewById<TextView>(R.id.id_subtitle)
        if (!TextUtils.isEmpty(suit.summary)) {
            subTitleView.text = suit.summary
        }
        else {
            subTitleView.text = "~~"
        }

        var coverView = holder.itemView.findViewById<ImageView>(R.id.id_cover)
        var coverUrl = appCtx.getBaseServerUrl() + "/" + suit.cover
        Log.i(TAG, "coverUrl: $coverUrl")
        Glide.with(coverView).load(coverUrl).into(coverView)
    }

}
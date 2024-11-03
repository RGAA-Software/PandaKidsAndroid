package com.tc.reading.ui.video

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tc.reading.R
import com.tc.reading.entity.Video
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.PageStyle

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoHolder>() {
    private lateinit var mViewPager: BannerViewPager<Video>
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

            var videos = mutableListOf<Video>()
            videos.add(Video());
            videos.add(Video());
            videos.add(Video());
            videos.add(Video());
            videos.add(Video());
            videos.add(Video());

            mViewPager.refreshData(videos);
        } else {
            view = View.inflate(parent.context, R.layout.item_video, null)
        }
        return VideoHolder(view);
    }

    override fun getItemCount(): Int {
        return 15;
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 0
        }
        return 1
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {

    }

}
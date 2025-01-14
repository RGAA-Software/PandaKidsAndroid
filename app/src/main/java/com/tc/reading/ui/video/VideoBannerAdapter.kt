package com.tc.reading.ui.video

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.VideoSuit
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class VideoBannerAdapter(private var appCtx: AppContext) : BaseBannerAdapter<VideoSuit>() {

    override fun bindData(holder: BaseViewHolder<VideoSuit>, data: VideoSuit?, position: Int, pageSize: Int) {
        if (data == null) {
            return;
        }
        //holder.setImageResource(R.id.banner_image, R.drawable.test_cover)
        val coverView = holder.itemView.findViewById<ImageView>(R.id.banner_image)
        val imageUrl = appCtx.getBaseServerUrl() + "/" + data.cover
        Glide.with(holder.itemView).load(imageUrl).into(coverView)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner;
    }
}

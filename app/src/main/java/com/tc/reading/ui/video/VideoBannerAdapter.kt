package com.tc.reading.ui.video

import com.tc.reading.R
import com.tc.reading.entity.Video
import com.tc.reading.entity.VideoSuit
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class SimpleAdapter : BaseBannerAdapter<VideoSuit>() {

    override fun bindData(holder: BaseViewHolder<VideoSuit>, data: VideoSuit?, position: Int, pageSize: Int) {
        if (data == null) {
            return;
        }
        holder.setImageResource(R.id.banner_image, R.drawable.test_cover)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner;
    }
}

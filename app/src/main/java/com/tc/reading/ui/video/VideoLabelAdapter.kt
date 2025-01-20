package com.tc.reading.ui.video

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tc.reading.AppContext
import com.tc.reading.R

class VideoLabelAdapter(private var appCtx: AppContext,
    private var videoLabels: MutableList<VideoLabel>
    ): RecyclerView.Adapter<VideoLabelAdapter.VideoHolder>() {

    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        return VideoHolder(View.inflate(appCtx.getContext(), R.layout.item_video_label, null))
    }

    override fun getItemCount(): Int {
        return videoLabels.size
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {

    }
}
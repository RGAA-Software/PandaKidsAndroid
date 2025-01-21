package com.tc.reading.ui.labelfilter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tc.reading.AppContext
import com.tc.reading.R

class LabelFilterAdapter(private var appCtx: AppContext,
                         private var labelFilters: MutableList<LabelFilterItem>
    ): RecyclerView.Adapter<LabelFilterAdapter.VideoHolder>() {

    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        return VideoHolder(View.inflate(appCtx.getContext(), R.layout.item_label_filter, null))
    }

    override fun getItemCount(): Int {
        return labelFilters.size
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {

    }
}
package com.tc.reading.ui.videolist

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.PkVideo

class VideoListAdapter(private var appCtx: AppContext,
                       private var videos: MutableList<PkVideo>)
    : RecyclerView.Adapter<VideoListAdapter.VideoHolder>() {

    private val TAG = "VideoListAdapter"
    var onVideoClickListener: OnVideoClickListener? = null

    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnVideoClickListener {
        fun onVideoClicked(video: PkVideo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view = View.inflate(parent.context, R.layout.item_video, null)
        return VideoHolder(view);
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        // click
        holder.itemView.setOnClickListener {
            if (onVideoClickListener != null) {
                val vs = videos[position]
                onVideoClickListener!!.onVideoClicked(vs)
            }
        }

        val video = videos[position]
        val titleView = holder.itemView.findViewById<TextView>(R.id.id_title)
        titleView.text = video.name

        val coverView = holder.itemView.findViewById<ImageView>(R.id.id_cover)
        val coverUrl = appCtx.getBaseServerUrl() + "/" + video.cover
        Log.i(TAG, "coverUrl: $coverUrl")
        Glide.with(coverView).load(coverUrl).into(coverView)
    }



}
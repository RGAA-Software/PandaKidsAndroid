package com.tc.reading.ui.audio

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.PkAudio

class AudioListAdapter(private var appCtx: AppContext,
                       private var audios: MutableList<PkAudio>)
    : RecyclerView.Adapter<AudioListAdapter.VideoHolder>() {

    private val TAG = "AudioListAdapter"
    var onAudioClickListener: OnAudioClickListener? = null

    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnAudioClickListener {
        fun onAudioClicked(book: PkAudio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view = View.inflate(parent.context, R.layout.item_audio, null)
        return VideoHolder(view);
    }

    override fun getItemCount(): Int {
        return audios.size
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        // click
        holder.itemView.setOnClickListener {
            if (onAudioClickListener != null) {
                val vs = audios[position]
                onAudioClickListener!!.onAudioClicked(vs)
            }
        }

        val book = audios[position]
        val titleView = holder.itemView.findViewById<TextView>(R.id.id_title)
        titleView.text = book.name

        val coverView = holder.itemView.findViewById<ImageView>(R.id.id_cover)
        val coverUrl = appCtx.getBaseServerUrl() + "/" + book.cover
        Log.i(TAG, "coverUrl: $coverUrl")
        Glide.with(coverView).load(coverUrl).into(coverView)
    }



}
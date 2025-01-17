package com.tc.reading.ui.audio

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.PkAudioSuit

class AudioSuitAdapter(private var appCtx: AppContext,
                       private var activity: Activity,
                       private var audioSuits: MutableList<PkAudioSuit>) :
    RecyclerView.Adapter<AudioSuitAdapter.BookViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(audioSuit: PkAudioSuit)
    }

    class BookViewHolder(itemView: View) : ViewHolder(itemView) {
        var btn = itemView.findViewById<ImageView>(R.id.book_cover);
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        if (viewType == 0) {
            val view = View.inflate(activity, R.layout.item_audio_suit_filter, null);
            return BookViewHolder(view);
        } else {
            val view = View.inflate(activity, R.layout.item_audio_suit, null);
            return BookViewHolder(view);
        }
    }

    override fun getItemCount(): Int {
        return audioSuits.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 0
        }
        return 1
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        if (position == 0) {
            return
        }
        val suit = audioSuits[position-1]
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClicked(suit)
            }
        }
    }

}
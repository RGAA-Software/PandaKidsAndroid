package com.tc.reading.ui.book

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.PkBook

class BookListAdapter(private var appCtx: AppContext,
                      private var books: MutableList<PkBook>)
    : RecyclerView.Adapter<BookListAdapter.VideoHolder>() {

    private val TAG = "VideoListAdapter"
    var onBookClickListener: OnBookClickListener? = null

    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnBookClickListener {
        fun onBookClicked(book: PkBook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view = View.inflate(parent.context, R.layout.item_video, null)
        return VideoHolder(view);
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        // click
        holder.itemView.setOnClickListener {
            if (onBookClickListener != null) {
                val vs = books[position]
                onBookClickListener!!.onBookClicked(vs)
            }
        }

        val book = books[position]
        val titleView = holder.itemView.findViewById<TextView>(R.id.id_title)
        titleView.text = book.name

        val coverView = holder.itemView.findViewById<ImageView>(R.id.id_cover)
        val coverUrl = appCtx.getBaseServerUrl() + "/" + book.cover
        Log.i(TAG, "coverUrl: $coverUrl")
        Glide.with(coverView).load(coverUrl).into(coverView)
    }



}
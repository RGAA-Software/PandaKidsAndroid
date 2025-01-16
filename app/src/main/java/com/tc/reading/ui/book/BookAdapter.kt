package com.tc.reading.ui.book

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.databinding.ItemBookBinding
import com.tc.reading.entity.PkBookSuit

class BookAdapter(private var appCtx: AppContext,
                  private var activity: Activity,
                  private var bookSuits: MutableList<PkBookSuit>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(bookSuit: PkBookSuit)
    }

    class BookViewHolder(itemView: View) : ViewHolder(itemView) {
        var btn = itemView.findViewById<ImageView>(R.id.book_cover);
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        if (viewType == 0) {
            val view = View.inflate(activity, R.layout.item_book_filter, null);
            return BookViewHolder(view);
        } else {
            val view = View.inflate(activity, R.layout.item_book, null);
            return BookViewHolder(view);
        }
    }

    override fun getItemCount(): Int {
        return bookSuits.size + 1
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
        val suit = bookSuits[position-1]
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClicked(suit)
            }
        }
    }

}
package com.tc.reading.ui.book

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tc.reading.R

class BookAdapter(private var context: Context, private var books: MutableList<BookInfo>)
    : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : ViewHolder(itemView) {
        var btn: Button = itemView.findViewById<Button>(R.id.test_btn);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = View.inflate(context, R.layout.item_book, null);
        return BookViewHolder(view);
    }

    override fun getItemCount(): Int {
        return books.size;
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.btn.setOnClickListener {

        }
    }

}
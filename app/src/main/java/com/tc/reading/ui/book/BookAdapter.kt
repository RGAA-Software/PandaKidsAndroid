package com.tc.reading.ui.book

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tc.reading.R
import com.tc.reading.databinding.ItemBookBinding

class BookAdapter(private var context: Context, private var books: MutableList<BookInfo>)
    : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {


    class BookViewHolder(itemView: View) : ViewHolder(itemView) {
        var btn = itemView.findViewById<ImageView>(R.id.book_cover);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = View.inflate(context, R.layout.item_book, null);
        return BookViewHolder(view);
    }

    override fun getItemCount(): Int {
        return books.size;
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Toast.makeText(context, "index:" + position, Toast.LENGTH_SHORT).show();

            context.startActivity(Intent(context, BookContentActivity::class.java));

        }
    }

}
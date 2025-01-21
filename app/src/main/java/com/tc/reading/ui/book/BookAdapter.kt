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
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.databinding.ItemBookBinding
import com.tc.reading.entity.PkBookSuit
import com.tc.reading.ui.labelfilter.LabelFilterAdapter
import com.tc.reading.ui.labelfilter.LabelFilterItem

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
    private var labelFilterAdapter: LabelFilterAdapter? = null
    private var labelFilterView: View? = null
    private var labelListView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        var view: View
        if (viewType == 0) {
            if (labelFilterView == null) {
                view = View.inflate(parent.context, R.layout.item_suit_label_filter, null)
                view.findViewById<Button>(R.id.id_expand_labels).setOnClickListener {
                    if (labelListView!!.visibility == View.VISIBLE) {
                        labelListView!!.visibility = View.GONE
                    } else {
                        labelListView!!.visibility = View.VISIBLE
                    }
                }

                labelFilterView = view
                labelListView = view.findViewById<RecyclerView>(R.id.id_labels)
                val mockData = mutableListOf<LabelFilterItem>()
                for (i in 0..20) {
                    mockData.add(LabelFilterItem())
                }
                labelFilterAdapter = LabelFilterAdapter(appCtx, mockData)
                labelListView!!.adapter = labelFilterAdapter
                var flexMgr = FlexboxLayoutManager(view.context, FlexDirection.ROW)
                flexMgr.justifyContent = JustifyContent.FLEX_START
                flexMgr.alignItems = AlignItems.CENTER
                labelListView!!.layoutManager = flexMgr
                labelFilterAdapter!!.notifyDataSetChanged()
            } else {
                view = labelFilterView!!
            }
            return BookViewHolder(view);
        } else {
            view = View.inflate(activity, R.layout.item_book, null);
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
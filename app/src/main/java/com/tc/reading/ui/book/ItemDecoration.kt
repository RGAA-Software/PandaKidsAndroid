package com.tc.reading.ui.book

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(private var dpSize: Int) : RecyclerView.ItemDecoration() {

    private val pixSize = (Resources.getSystem().displayMetrics.density * dpSize).toInt();

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = pixSize
        if (parent.getChildAdapterPosition(view) % 2 == 0) {
            outRect.left = pixSize
            outRect.right = pixSize
        } else if (parent.getChildAdapterPosition(view) % 2 == 1) {
            outRect.left = 0
            outRect.right = pixSize
        }
    }
}
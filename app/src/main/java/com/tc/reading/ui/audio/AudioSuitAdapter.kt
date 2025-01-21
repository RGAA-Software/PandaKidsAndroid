package com.tc.reading.ui.audio

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.tc.reading.AppContext
import com.tc.reading.R
import com.tc.reading.entity.PkAudioSuit
import com.tc.reading.ui.labelfilter.LabelFilterAdapter
import com.tc.reading.ui.labelfilter.LabelFilterItem

class AudioSuitAdapter(private var appCtx: AppContext,
                       private var activity: Activity,
                       private var audioSuits: MutableList<PkAudioSuit>) :
    RecyclerView.Adapter<AudioSuitAdapter.AudioViewHolder>() {

    private var labelFilterAdapter: LabelFilterAdapter? = null
    private var labelFilterView: View? = null

    interface OnItemClickListener {
        fun onItemClicked(audioSuit: PkAudioSuit)
    }

    class AudioViewHolder(itemView: View) : ViewHolder(itemView) {

    }

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val view: View
        if (viewType == 0) {
            if (labelFilterView == null) {
                view = View.inflate(parent.context, R.layout.item_suit_label_filter, null)
                labelFilterView = view
                val labelsView = view.findViewById<RecyclerView>(R.id.id_labels)
                val mockData = mutableListOf<LabelFilterItem>()
                for (i in 0..20) {
                    mockData.add(LabelFilterItem())
                }
                labelFilterAdapter = LabelFilterAdapter(appCtx, mockData)
                labelsView.adapter = labelFilterAdapter
                var flexMgr = FlexboxLayoutManager(view.context, FlexDirection.ROW)
                flexMgr.justifyContent = JustifyContent.FLEX_START
                flexMgr.alignItems = AlignItems.CENTER
                labelsView.layoutManager = flexMgr
                labelFilterAdapter!!.notifyDataSetChanged()
            } else {
                view = labelFilterView!!
            }
            return AudioViewHolder(view)
        } else {
            view = View.inflate(activity, R.layout.item_audio_suit, null);
            return AudioViewHolder(view);
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

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
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
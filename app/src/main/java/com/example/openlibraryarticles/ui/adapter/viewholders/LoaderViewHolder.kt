package com.example.openlibraryarticles.ui.adapter.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_load_item.view.*

class LoaderViewHolder (view:View):RecyclerView.ViewHolder(view) {

    fun bind(hideFooter : Boolean) {
        // this view holder just view the loading progress bar
        itemView.loadingProgressBar.visibility = if (hideFooter) View.GONE else View.VISIBLE
    }

}
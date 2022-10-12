package com.example.openlibraryarticles.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.openlibraryarticles.R
import com.example.openlibraryarticles.ui.adapter.base.BaseAdapter
import com.example.openlibraryarticles.ui.adapter.viewholders.ImagesViewHolder

class ImageAdapter (private val images: ArrayList<String>) : BaseAdapter<String>(){

    init {
        arrayList = images
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val view = layoutInflater.inflate(R.layout.cell_image, parent, false)
       return ImagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImagesViewHolder).bind(images[position])
    }

}
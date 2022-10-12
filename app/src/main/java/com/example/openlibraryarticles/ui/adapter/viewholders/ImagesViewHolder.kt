package com.example.openlibraryarticles.ui.adapter.viewholders

import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.cell_image.view.*

class ImagesViewHolder(view: View):RecyclerView.ViewHolder(view) {
    fun bind(imageUri: String) {

        Glide.with(itemView.context).load(imageUri)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {

                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // show the isbn image when image loaded successfully
                    itemView.article_image.visibility = View.VISIBLE
                    return false
                }

            })
            .into(itemView.article_image)

    }


}
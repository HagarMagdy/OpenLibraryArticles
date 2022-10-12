package com.example.openlibraryarticles.ui.adapter.viewholders

import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.openlibraryarticles.model.domain.Article
import kotlinx.android.synthetic.main.cell_article.view.*
import kotlinx.coroutines.channels.Channel

class ArticleViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    fun bind(
        item: Article,
        actionChannel: Channel<Article>

    ) {
        itemView.title.text = item.title
        itemView.dateTextView.text = item.publishedDate
        itemView.byline.text = item.byline

     // load the image from the url using Glide
        if (item.media.size>0)
        Glide.with(itemView.context).load(item.media.get(0).mediaMetadata.get(0).url)
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
                    itemView.articleImage.visibility = View.VISIBLE
                    return false
                }

            })
            .into(itemView.articleImage)

        // handle on click on the article cell

        itemView.details_btn.setOnClickListener {
            // disable cell to prevent multiple clicks
            it.isEnabled = false
            actionChannel.trySend(item)
            // this is to disable the multiple clicks on cell until the new screen is opened
            Handler(Looper.getMainLooper()).postDelayed({it.isEnabled = true}, 1000)

        }

        itemView.setOnClickListener {
            // disable cell to prevent multiple clicks
            it.isEnabled = false
            actionChannel.trySend(item)
            // this is to disable the multiple clicks on cell until the new screen is opened
            Handler(Looper.getMainLooper()).postDelayed({it.isEnabled = true}, 1000)
        }
    }
}
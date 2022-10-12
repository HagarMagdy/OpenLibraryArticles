package com.example.openlibraryarticles.ui.activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.openlibraryarticles.R
import com.example.openlibraryarticles.model.domain.Article
import com.example.openlibraryarticles.ui.adapter.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var imageAdapter: ImageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        onNewIntent(intent)

        backImg.setOnClickListener {
            finish()
        }
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let {
            if (it.hasExtra(ARTICLE_KEY)) {
                val articleObj = it.extras?.let { extras -> extras[ARTICLE_KEY] as Article }
                articleObj?.let { article -> fillUI(article) }
            }
        }

    }

    private fun fillUI(article: Article) {

        art_title.text=article.title
        publish_date.text= article.publishedDate


        source.text=article.source
        description.text=article.abstract

        if (article.media.size>0)
            Glide.with(this).load(article.media[0].mediaMetadata[1].url)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        // hide the isbn image if there is no image to preview
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
                        poster.visibility = View.VISIBLE
                        return false
                    }

                })
                .into(poster)

    }
}
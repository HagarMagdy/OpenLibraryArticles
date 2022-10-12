package com.example.openlibraryarticles.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.openlibraryarticles.R
import com.example.openlibraryarticles.model.domain.Article
import com.example.openlibraryarticles.ui.adapter.base.BaseAdapter
import com.example.openlibraryarticles.ui.adapter.base.TYPE_FOOTER
import com.example.openlibraryarticles.ui.adapter.base.TYPE_ITEM
import com.example.openlibraryarticles.ui.adapter.viewholders.ArticleViewHolder
import com.example.openlibraryarticles.ui.adapter.viewholders.LoaderViewHolder
import kotlinx.coroutines.channels.Channel


class ArticlesAdapter(
    private val articles: ArrayList<Article>,
    val actionChannel: Channel<Article>
) : BaseAdapter<Article>() {

    init {
        arrayList = articles
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_FOOTER) {
            val view = layoutInflater.inflate(R.layout.cell_load_item, parent, false)
            LoaderViewHolder(view)
        } else {
            val view = layoutInflater.inflate(R.layout.cell_article, parent, false)
            ArticleViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) != TYPE_FOOTER)
            (holder as ArticleViewHolder).bind(articles[position],actionChannel)
        else
            (holder as LoaderViewHolder).bind(hideFooter)
    }


    /**
     * Function to get the cell type using position
     * @return it returns @TYPE_ITEM or @TYPE_FOOTER if it's loading
     */
    override fun getItemViewType(position: Int): Int {
        return if (arrayList.size - 1 >= position) TYPE_ITEM else TYPE_FOOTER
    }
}
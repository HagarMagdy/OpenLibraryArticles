package com.example.openlibraryarticles.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openlibraryarticles.R
import com.example.openlibraryarticles.model.domain.Article
import com.example.openlibraryarticles.ui.adapter.ArticlesAdapter
import com.example.openlibraryarticles.ui.viewmodels.MainStateEvent
import com.example.openlibraryarticles.ui.viewmodels.MainViewModel
import com.example.openlibraryarticles.utills.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


const val ARTICLE_KEY = "article_key"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var articlesAdapter: ArticlesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        subscribeObservers()
        attachListeners()

        viewModel.setStateEvent(MainStateEvent.GetArticleEvent)
    }

    private fun setupUI() {
        listRecycler.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun subscribeObservers() {
        // observe on data state
        viewModel.dataState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success<List<Article>> -> {
                    displayProgressBar(false)
                    appendArticles(dataState.data)
                    listRecycler.scrollToPosition(0)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
                is DataState.CLEAR -> {
                    displayProgressBar(false)
                    articlesAdapter.clearList()
                }
            }
        }


        // listen to recycler view holder's click listener

        lifecycleScope.launch {
            articlesAdapter.actionChannel.consumeAsFlow().collect {
                openDetailsActivity(it)
            }
        }

    }
    private fun openDetailsActivity(article: Article) {
        val detailsIntent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(ARTICLE_KEY, article)
        }
        startActivity(detailsIntent)
    }
    private fun attachListeners() {
        // refresh swipe to refresh Layout
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.setStateEvent(MainStateEvent.RefreshScreenEvent)
            listRecycler.scrollToPosition(0)
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        articlesAdapter.updateHideFooter(!isDisplayed)
        if (isDisplayed)
            listRecycler.scrollToPosition(articlesAdapter.itemCount - 1)
        else
            swipeRefreshLayout.isRefreshing = false
    }

    private fun displayError(message: String?) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    private fun appendArticles(articles: List<Article>) {
        articlesAdapter.addList(ArrayList(articles))
    }
}
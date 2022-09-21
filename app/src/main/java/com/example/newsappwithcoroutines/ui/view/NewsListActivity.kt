package com.example.newsappwithcoroutines.ui.view




import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappwithcoroutines.R
import com.example.newsappwithcoroutines.data.api.ApiHelperImp
import com.example.newsappwithcoroutines.data.api.RetrofitBuilder
import com.example.newsappwithcoroutines.data.model.News
import com.example.newsappwithcoroutines.ui.adapter.NewsListAdapter
import com.example.newsappwithcoroutines.ui.base.ViewModelFactory
import com.example.newsappwithcoroutines.ui.viewmodel.NewsListViewModel
import com.example.newsappwithcoroutines.ui.viewmodel.ParallelNewsListViewModel
import com.example.newsappwithcoroutines.ui.viewmodel.SerialNewsListViewModel
import com.example.newsappwithcoroutines.utils.Status
import kotlinx.android.synthetic.main.activity_news_list.*


class NewsListActivity : AppCompatActivity() {

    lateinit var newsListViewModel: ParallelNewsListViewModel
    lateinit var newsListAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        setupUI()
        setupViewModel()
        setUpObserver()

    }

    private fun setupUI() {
        newsListAdapter = NewsListAdapter(arrayListOf())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@NewsListActivity)
            adapter = newsListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }
    }

    private fun setUpObserver() {
        newsListViewModel.getNews().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { newsList -> renderList(newsList) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(newsList: List<News>) {
        newsListAdapter.addData(newsList)
        newsListAdapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        newsListViewModel = ViewModelProviders.of(
            this ,
            ViewModelFactory(ApiHelperImp(RetrofitBuilder.apiService))
        ).get(ParallelNewsListViewModel::class.java)
    }
}
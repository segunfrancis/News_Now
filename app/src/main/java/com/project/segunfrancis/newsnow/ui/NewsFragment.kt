package com.project.segunfrancis.newsnow.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager

import com.project.segunfrancis.newsnow.R
import com.project.segunfrancis.newsnow.adapter.NewsRecyclerAdapter
import com.project.segunfrancis.newsnow.model.News
import com.project.segunfrancis.newsnow.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment(R.layout.fragment_news) {

    lateinit var adapter: NewsRecyclerAdapter
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var tempContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.isRefreshing = true
        adapter = NewsRecyclerAdapter()
        populateRecyclerView()
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))

        swipeRefreshLayout.setOnRefreshListener {
            populateRecyclerView()
        }
    }

    private fun populateRecyclerView() {
        viewModel.getNewsLiveData().observe(requireActivity(),
            Observer<List<News>?> {
                if (it!!.isNotEmpty()) {
                    adapter.addData(it)
                    recyclerView.layoutManager = LinearLayoutManager(tempContext)
                    recyclerView.adapter = adapter
                    swipeRefreshLayout.isRefreshing = false
                    Log.d("NewsFragment", it.toString())
                }
            })
    }

    override fun onAttach(context: Context) {
        tempContext = context
        super.onAttach(context)
    }
}

package com.project.segunfrancis.newsnow.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.google.firebase.database.*

import com.project.segunfrancis.newsnow.R
import com.project.segunfrancis.newsnow.adapter.NewsRecyclerAdapter
import com.project.segunfrancis.newsnow.model.News
import kotlinx.android.synthetic.main.fragment_news.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment(R.layout.fragment_news) {

    lateinit var adapter: NewsRecyclerAdapter
    private var newsList: List<News> = ArrayList()

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
        FirebaseDatabase.getInstance().getReference("news").limitToLast(100)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        (newsList as ArrayList).clear()
                        for (snapshot in dataSnapshot.children) {
                            (newsList as ArrayList).add(snapshot.getValue(News::class.java)!!)
                        }
                        (newsList as ArrayList).reverse()
                        adapter.addData(newsList)
                        recyclerView.adapter = adapter
                        swipeRefreshLayout.isRefreshing = false
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("NewsFragment", error.details)
                        swipeRefreshLayout.isRefreshing = false
                    }
                })
    }
}

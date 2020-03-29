package com.project.segunfrancis.newsnow.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import coil.api.load
import com.project.segunfrancis.newsnow.R
import com.project.segunfrancis.newsnow.model.News
import kotlinx.android.synthetic.main.item_news.view.*
import java.util.*

/**
 * Created by SegunFrancis
 */
class NewsRecyclerAdapter : RecyclerView.Adapter<NewsRecyclerAdapter.NewsRecyclerViewHolder>() {

    private var data: List<News>? = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRecyclerViewHolder {
        return NewsRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news, parent, false)
        )
    }

    override fun getItemCount() = data!!.size

    override fun onBindViewHolder(holder: NewsRecyclerViewHolder, position: Int) =
        holder.bind(data!![position])

    fun addData(data: List<News>?) {
        this.data = data
        notifyDataSetChanged()
    }

    class NewsRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: News) = with(itemView) {
            itemView.newsThumbnailImageView.load(R.drawable.letter_n) {
                placeholder(R.drawable.letter_n)
                crossfade(true)
            }
            itemView.newsTitleTextView.text = item.title
            itemView.pubDate.text = item.pubDate

            itemView.setOnClickListener {
                findNavController().navigate(
                    R.id.action_newsFragment_to_webViewFragment,
                    bundleOf("URL" to item.link)
                )
            }
        }
    }
}
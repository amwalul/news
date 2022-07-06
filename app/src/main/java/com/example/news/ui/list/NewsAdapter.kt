package com.example.news.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.data.source.remote.response.ArticleData
import com.example.news.databinding.ItemNewsBinding

class NewsAdapter(
    private val onItemClickListener: (item: ArticleData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ArticleData>() {
        override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData) =
            oldItem == newItem
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewsViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ArticleData>) {
        differ.submitList(list)
    }

    private class NewsViewHolder(
        private val binding: ItemNewsBinding,
        private val onItemClickListener: (item: ArticleData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArticleData) = binding.apply {
            root.setOnClickListener {
                onItemClickListener(item)
            }

            Glide.with(binding.root)
                .load(item.urlToImage)
                .into(ivImage)

            tvTitle.text = item.title
            tvDescription.text = item.description
        }
    }
}
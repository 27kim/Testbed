package io.lab27.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.model.Article
import io.lab27.githubuser.databinding.ItemNewsBinding
import io.lab27.githubuser.databinding.LayoutRecyclerviewBinding

class NewsAdapter (private val lifecycleOwner: LifecycleOwner):
    ListAdapter<Article, NewsAdapter.Holder>(DataDiff) {

    var onItemClick: ((User, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(DataBindingUtil.inflate(inflater, R.layout.item_news, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner
        holder.binding.news = getItem(position)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_news
    }

    class Holder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    object DataDiff : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}
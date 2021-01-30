package io.lab27.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.data.model.Article
import io.lab27.githubuser.databinding.ItemBannerBinding

class NewsAdapter(private val lifecycleOwner: LifecycleOwner) :
    ListAdapter<Article, NewsAdapter.Holder>(diffCallback) {

    var onClick: ((Article) -> Unit?)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(DataBindingUtil.inflate(inflater, R.layout.item_banner, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner
        holder.binding.article = getItem(position)
        holder.binding.root.setOnClickListener {
            Toast.makeText(it.context, "$position", Toast.LENGTH_SHORT).show()
            onClick?.invoke(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_banner

    class Holder(val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallback by lazy {
            object : DiffUtil.ItemCallback<Article>() {
                override fun areItemsTheSame(old: Article, new: Article) = old == new
                override fun areContentsTheSame(old: Article, new: Article): Boolean = old == new
            }
        }
    }
}
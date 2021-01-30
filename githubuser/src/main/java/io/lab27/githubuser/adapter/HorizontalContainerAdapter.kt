package io.lab27.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.ui.main.NewsAdapter.Companion.diffCallback
import io.lab27.githubuser.data.model.Article
import io.lab27.githubuser.databinding.ItemHorizontalContainerBinding

class HorizontalContainerAdapter(
    private val lifecycleOwner: LifecycleOwner,
    val adapter: ListAdapter<*, *>
) : ListAdapter<Article, HorizontalContainerAdapter.Holder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_horizontal_container,
            parent,
            false
        ), adapter
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner
    }

    override fun getItemCount(): Int = 1

    override fun getItemViewType(position: Int): Int = R.layout.item_horizontal_container

    class Holder(val binding: ItemHorizontalContainerBinding, adapter: ListAdapter<*, *>) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                horizontalRecyclerView.layoutManager = LinearLayoutManager(
                    binding.horizontalRecyclerView.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                horizontalRecyclerView.adapter = adapter
            }
        }
    }
}
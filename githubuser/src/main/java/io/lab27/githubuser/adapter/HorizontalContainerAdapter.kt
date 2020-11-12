package io.lab27.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.data.model.Article
import io.lab27.githubuser.databinding.ItemHorizontalContainerBinding

class HorizontalContainerAdapter(private val lifecycleOwner: LifecycleOwner, val adapter : ListAdapter<*, *>) :
    ListAdapter<Article, HorizontalContainerAdapter.Holder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(DataBindingUtil.inflate(inflater, R.layout.item_horizontal_container, parent, false), adapter)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_horizontal_container
    }

    override fun getItemCount(): Int {
         return 1
    }

    class Holder(val binding: ItemHorizontalContainerBinding, adapter: ListAdapter<*, *>) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.recyclerView.layoutManager = LinearLayoutManager(binding.recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerView.adapter = adapter
        }
    }

    companion object {
        val diffCallback by lazy {
            object :
                DiffUtil.ItemCallback<Article>() {

                override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}
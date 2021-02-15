package io.lab27.githubuser.adapter

import android.content.res.Resources
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import io.lab27.githubuser.R
import io.lab27.githubuser.data.model.Article
import io.lab27.githubuser.databinding.ItemHorizontalContainerBinding
import io.lab27.githubuser.ui.main.NewsAdapter.Companion.diffCallback

class HorizontalContainerAdapter(
    private val lifecycleOwner: LifecycleOwner,
    val adapter: ListAdapter<*, *>
) : ListAdapter<Article, HorizontalContainerAdapter.Holder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_horizontal_container,
                parent,
                false
            ), adapter
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner
    }

    override fun getItemCount(): Int = 1

    override fun getItemViewType(position: Int): Int = R.layout.item_horizontal_container

    inner class Holder(val binding: ItemHorizontalContainerBinding, adapter: ListAdapter<*, *>) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.horizontalRecyclerView.adapter = adapter
        }
    }
}

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

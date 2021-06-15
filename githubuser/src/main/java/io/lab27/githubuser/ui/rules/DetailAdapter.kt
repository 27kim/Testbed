package io.lab27.githubuser.ui.rules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.databinding.ItemRecyclerviewTestBinding

class DetailAdapter : ListAdapter<RvTest, DetailAdapter.ViewHolder>(RvTest.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemRecyclerviewTestBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = getItem(position)
    }

    class ViewHolder(val binding: ItemRecyclerviewTestBinding) : RecyclerView.ViewHolder(binding.root)
}

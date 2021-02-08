package io.lab27.githubuser.ui.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.databinding.LayoutSettingBinding

class SettingAdapter(private val lifeCycleOwner: LifecycleOwner) :
    ListAdapter<String, SettingAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_setting,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            lifecycleOwner = lifeCycleOwner
            item = getItem(position)
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.layout_setting

    inner class ViewHolder(val binding: LayoutSettingBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffUtil by lazy {
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
                override fun areContentsTheSame(oldItem: String, newItem: String) =
                    oldItem == newItem
            }
        }
    }
}
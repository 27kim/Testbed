package io.lab27.githubuser.ui.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.databinding.LayoutAppListBinding

class AppListAdapter(private val lifeCycleOwner: LifecycleOwner,
                     private val layoutResId : Int
                   ) :
    ListAdapter<String, AppListAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutResId,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        with(holder.binding) {
            this.root.width
            this.lifecycleOwner = lifeCycleOwner
            this.item = getItem(position)
        }

    override fun getItemCount(): Int = currentList.size

    override fun getItemViewType(position: Int): Int = layoutResId

    inner class ViewHolder(val binding: LayoutAppListBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffUtil by lazy {
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                    oldItem == newItem
            }
        }
    }
}

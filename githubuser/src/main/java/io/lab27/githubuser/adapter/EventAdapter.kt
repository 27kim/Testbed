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
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.model.EventResponse
import io.lab27.githubuser.databinding.ItemEventBinding
import io.lab27.githubuser.databinding.ItemUserBinding

class EventAdapter (private val lifecycleOwner: LifecycleOwner):
    ListAdapter<EventResponse, EventAdapter.Holder>(DataDiff) {

    var onItemClick: ((EventResponse) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(DataBindingUtil.inflate(inflater, R.layout.item_event, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner
        holder.binding.event = getItem(position)
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_user
    }

    class Holder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root)

    object DataDiff : DiffUtil.ItemCallback<EventResponse>() {
        override fun areItemsTheSame(oldItem: EventResponse, newItem: EventResponse): Boolean {
            return oldItem.evntRegSeq == newItem.evntRegSeq
        }

        override fun areContentsTheSame(oldItem: EventResponse, newItem: EventResponse): Boolean {
            return oldItem == newItem
        }
    }
}
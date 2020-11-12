package io.lab27.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.databinding.LayoutRecyclerviewBinding

class RemoteAdapter (private val lifecycleOwner: LifecycleOwner):
    ListAdapter<User, RemoteAdapter.Holder>(UserPagingAdapter.DataDiff) {

    var onItemClick: ((User, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(DataBindingUtil.inflate(inflater, R.layout.layout_recyclerview, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner
        holder.binding.user = getItem(position)
        holder.binding.isStarred.setOnClickListener {
            val user = getItem(position)
            user.isFavorite = !user.isFavorite
            onItemClick?.invoke(user, position)
            holder.binding.user = user
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.layout_recyclerview
    }

    class Holder(val binding: LayoutRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)
}
package io.lab27.githubuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.adapter.UserPagingAdapter
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.databinding.ItemUserBinding

class RemoteAdapter (private val lifecycleOwner: LifecycleOwner):
    ListAdapter<User, RemoteAdapter.Holder>(UserPagingAdapter.DataDiff) {

    var onClick: ((User, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(DataBindingUtil.inflate(inflater, R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner
        holder.binding.user = getItem(position)
        holder.binding.isStarred.setOnClickListener {
            val user = getItem(position)
            user.isFavorite = !user.isFavorite
            onClick?.invoke(user, position)
            holder.binding.user = user
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_user
    }

    class Holder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}
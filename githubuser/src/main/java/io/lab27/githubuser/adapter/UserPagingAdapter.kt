package io.lab27.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.lab27.githubuser.R
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.databinding.FragmentPagingBinding
import kotlinx.android.synthetic.main.layout_recyclerview.view.*

class UserPagingAdapter : PagingDataAdapter<User, UserPagingAdapter.PagingViewHolder>(DataDiff) {
    lateinit var binding : FragmentPagingBinding
    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let{
            holder.onBind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return PagingViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_recyclerview, parent, false)
        )
    }

    class PagingViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(user: User) {
            view.tvTitle.text = user.login.toString()
            view.tvUrl.text = user.url
            Glide.with(view.context).load(user.avatar_url).into(view.ivImage)
        }
    }


    object DataDiff : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
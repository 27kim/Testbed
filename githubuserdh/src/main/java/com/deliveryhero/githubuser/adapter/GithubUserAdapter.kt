package com.deliveryhero.githubuser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deliveryhero.githubuser.R
import com.deliveryhero.githubuser.network.User
import com.deliveryhero.githubuser.databinding.RvLayoutUserListBinding
//
//class GithubUserAdapter(
//    private val context: Context,
//    private var userList: MutableList<User>
//) : RecyclerView.Adapter<GithubUserAdapter.GithubUserViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
//
//        val binding = DataBindingUtil.inflate<RvLayoutUserListBinding>(
//            LayoutInflater.from(context),
//            R.layout.rv_layout_user_list, parent,
//            false
//        )
//
//        binding.userFavorite.apply {
//            setOnClickListener {
//
//            }
//        }
//
//        return GithubUserViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int {
//        return userList.size
//    }
//
//    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
//        holder.onBind(userList[position])
//    }
//
//    fun updateUser(list: List<User>) {
//        userList.clear()
//        userList.addAll(list)
//        notifyDataSetChanged()
//    }
//
//    inner class GithubUserViewHolder(private var binding: RvLayoutUserListBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun onBind(user: User) {
//            binding.user = user
//            Glide.with(binding.root).load(user.avatar_url).placeholder(R.drawable.ic_error)
//                .into(binding.userAvatar)
//        }
//    }
//}
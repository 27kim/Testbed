package io.lab27.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.databinding.LayoutRecyclerviewBinding

class MainAdapter(private var items: List<User>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    var onItemClick: ((User, Int) -> Unit)? = null
    lateinit var itemBinding: LayoutRecyclerviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        itemBinding =
            LayoutRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    fun updateItem(position: Int) {
        notifyItemChanged(position)
    }

    fun submitList(items: List<User>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: LayoutRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(user: User) {
            binding.user = user
            binding.isStarred.setOnClickListener {
                binding.user?.let { user ->
                    user.isFavorite = !user.isFavorite
//                    L.i("isStarred modified : ${user.isFavorite}")
                    onItemClick?.invoke(user, adapterPosition)
                    binding.user = user
                }
            }
        }
    }
}
package io.lab27.githubuser.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.databinding.LayoutRecyclerviewBinding

class MainAdapter :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var items = mutableListOf<User>()
    var onItemClick: ((User) -> Unit)? = null
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

    fun updateItem(position : Int){
        notifyDataSetChanged()
    }

    fun submitList(items: List<User>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: LayoutRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }

        fun onBind(user: User) {
            binding.isStarred.setOnClickListener {
                binding.user?.let { user ->
                    Log.i("isStarred", "isStarred clicked : ${user.isFavorite}")
                    user.isFavorite = !user.isFavorite
                    Log.i("isStarred", "isStarred modified : ${user.isFavorite}")
                    onItemClick?.invoke(user)
                }
            }
            binding.user = user
        }
    }
}
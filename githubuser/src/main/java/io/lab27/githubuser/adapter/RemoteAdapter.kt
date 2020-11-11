package io.lab27.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.databinding.LayoutRecyclerviewBinding
import io.lab27.githubuser.util.L
import io.lab27.githubuser.viewmodel.UserViewModel

class RemoteAdapter (private val lifecycleOwner: LifecycleOwner, viewModel: UserViewModel):
    ListAdapter<User, RemoteAdapter.Holder>(diffCallback) {

    var onItemClick: ((User, Int) -> Unit)? = null

    init {
        viewModel.userList.observe(lifecycleOwner, Observer {
            L.i("Remote Adapter user list: $it")
            submitList(it)
        })
    }

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

    companion object {
        val diffCallback by lazy {
            object :
                DiffUtil.ItemCallback<User>() {

                override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}
/*
ListAdapter<User, MainViewHolder>(diffCallback)
//    RecyclerView.Adapter<MainViewHolder>()
{
    var onItemClick: ((User, Int) -> Unit)? = null
    lateinit var itemBinding: LayoutRecyclerviewBinding
    private var items: List<User> = mutableListOf()

    init {
        viewModel.userList.observe(lifecycleOwner, Observer {
            submitList(it)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        itemBinding =
            LayoutRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(itemBinding, onItemClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner
        getItem(position)?.let{
            holder.onBind(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.layout_recyclerview
    }

    fun updateItem(position: Int) {
        notifyItemChanged(position)
    }

//    fun submitList(items: List<User>) {
//        this.items = items
//        notifyDataSetChanged()
//    }

    companion object {
        val diffCallback by lazy {
            object :
                DiffUtil.ItemCallback<User>() {

                override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}

class MainViewHolder(
    val binding: LayoutRecyclerviewBinding,
    private val onItemClick: ((User, Int) -> Unit)?
) :
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
}*/

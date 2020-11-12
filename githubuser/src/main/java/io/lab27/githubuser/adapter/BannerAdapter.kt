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
import io.lab27.githubuser.databinding.ItemBannerBinding
import io.lab27.githubuser.viewmodel.NewsViewModel

class BannerAdapter(private val lifecycleOwner: LifecycleOwner, viewModel: NewsViewModel) :
    ListAdapter<String, BannerAdapter.Holder>(diffCallback) {

    init {
        viewModel.bannerItem.observe(lifecycleOwner, Observer {
            submitList(it)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(DataBindingUtil.inflate(inflater, R.layout.item_banner, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_banner
    }

    class Holder(val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallback by lazy {
            object :
                DiffUtil.ItemCallback<String>() {

                override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}
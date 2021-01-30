package io.lab27.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.databinding.ItemFooterBinding

class FooterAdapter(val lifecycleOwner: LifecycleOwner) :
    ListAdapter<String, FooterAdapter.Holder>(diffCallback) {

    var onClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(
            DataBindingUtil.inflate(inflater, R.layout.item_footer, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.lifecycleOwner = lifecycleOwner
        holder.binding.lblAction.text = "Add Article"
        holder.binding.container.setOnClickListener {
            onClick?.invoke()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_footer
    }

    override fun getItemCount() = 1

    class Holder(val binding: ItemFooterBinding) : RecyclerView.ViewHolder(binding.root)

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
package io.lab27.githubuser.ui.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.databinding.LayoutMenuBinding

class MenuAdapter(private val lifeCycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_menu,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        with(holder.binding) {
            this.lifecycleOwner = lifeCycleOwner
        }

    override fun getItemCount(): Int = 1

    override fun getItemViewType(position: Int): Int = R.layout.layout_menu

    inner class ViewHolder(val binding: LayoutMenuBinding) : RecyclerView.ViewHolder(binding.root)
}

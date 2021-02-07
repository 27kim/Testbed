package io.lab27.githubuser.ui.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.databinding.LayoutUserBinding

class UserAdapter(private val lifeCycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_user,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            lifecycleOwner = lifeCycleOwner
        }
    }

    override fun getItemCount(): Int = 1

    override fun getItemViewType(position: Int): Int = R.layout.layout_user


    inner class ViewHolder(val binding: LayoutUserBinding) : RecyclerView.ViewHolder(binding.root)
}

/*import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.base.BaseViewModelBindingAdapter
import io.lab27.githubuser.databinding.LayoutUserBinding

class UserAdapter(lifeCycleOwner : LifecycleOwner) : BaseViewModelBindingAdapter(lifeCycleOwner,null , R.layout.layout_user){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
    }
}*/
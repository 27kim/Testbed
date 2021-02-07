package io.lab27.githubuser.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

open class BaseViewModelBindingAdapter(
    private val lifeCycleOwner : LifecycleOwner,
    private val viewModel: BaseViewModel?,
    private val layoutResId: Int
) : RecyclerView.Adapter<BaseViewModelBindingAdapter.ViewHolder>() {
    lateinit var binding: ViewDataBinding
    override fun getItemCount() = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    = ViewHolder(DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        layoutResId,
        parent,
        false
    ))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        with(holder.binding) {
            viewModel?.let{
                setVariable(BR.viewModel, viewModel)
            }
            this.lifecycleOwner = lifeCycleOwner
            executePendingBindings()
        }

    inner class ViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

}
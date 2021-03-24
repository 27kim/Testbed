package io.lab27.githubuser.ui.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import io.lab27.githubuser.R
import io.lab27.githubuser.data.datasource.remote.MenuModel
import io.lab27.githubuser.ui.more.adapter.MenuListAdapter.Companion.diffUtil

class MoreSettingAdapter(private val lifecycleOwner: LifecycleOwner) :
    ListAdapter<MenuModel, MenuListAdapter.CommonViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListAdapter.CommonViewHolder =
        when (viewType) {
            MenuModel.TITLE -> TitleViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_title,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("wrong type")
        }

    override fun onBindViewHolder(holder: MenuListAdapter.CommonViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


    override fun getItemViewType(position: Int): Int = getItemViewType(position)
    inner class MenuViewHolder(override val binding: ViewDataBinding) : MenuListAdapter.CommonViewHolder(binding)

    inner class TitleViewHolder(override val binding: ViewDataBinding) : MenuListAdapter.CommonViewHolder(binding)
}

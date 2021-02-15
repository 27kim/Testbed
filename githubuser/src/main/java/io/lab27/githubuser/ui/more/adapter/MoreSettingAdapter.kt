package io.lab27.githubuser.ui.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import io.lab27.githubuser.R
import io.lab27.githubuser.data.datasource.remote.MenuModel
import io.lab27.githubuser.databinding.LayoutHeaderBinding
import io.lab27.githubuser.databinding.LayoutMenuItemBinding
import io.lab27.githubuser.ui.more.adapter.MenuListAdapter.Companion.diffUtil

class MoreSettingAdapter(private val lifecycleOwner: LifecycleOwner) :
    ListAdapter<MenuModel, MenuListAdapter.CommonViewHolder>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuListAdapter.CommonViewHolder =
        when (viewType) {
            MenuModel.TITLE -> TitleViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_header,
                    parent,
                    false
                )
            )
            MenuModel.TYPE_MENU_1 -> MenuViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_menu_item,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("wrong type")
        }

    override fun onBindViewHolder(holder: MenuListAdapter.CommonViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                holder.binding.item = getItem(position)
            }
            is MenuViewHolder -> {
                holder.binding.item = getItem(position)
            }
        }
    }
    override fun getItemViewType(position: Int): Int = getItem(position).type

    inner class MenuViewHolder(override val binding: LayoutMenuItemBinding) :
        MenuListAdapter.CommonViewHolder(binding)

    inner class TitleViewHolder(override val binding: LayoutHeaderBinding) :
        MenuListAdapter.CommonViewHolder(binding)
}

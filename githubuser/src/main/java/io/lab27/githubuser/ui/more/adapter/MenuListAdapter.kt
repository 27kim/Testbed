package io.lab27.githubuser.ui.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.datasource.remote.MenuModel
import io.lab27.githubuser.databinding.LayoutMenu1Binding
import io.lab27.githubuser.databinding.LayoutTitleBinding

class MenuListAdapter(private val lifeCycleOwner: LifecycleOwner) :
    ListAdapter<MenuModel, MenuListAdapter.CommonViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return when (viewType) {
            MenuModel.TITLE -> TitleViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_title,
                    parent,
                    false
                )
            )
            MenuModel.TYPE_MENU_1 -> MenuViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_menu_1,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("MoreMenuAdapter : wrong type")
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        when (holder.itemViewType) {
            MenuModel.TITLE -> {
                (holder as TitleViewHolder).apply {
                    binding.item = getItem(position)
                    binding.lifecycleOwner = lifeCycleOwner
                }
            }
            MenuModel.TYPE_MENU_1 -> {
                (holder as MenuViewHolder).apply {
                    binding.item = getItem(position)
                    binding.lifecycleOwner = lifeCycleOwner
                }
            }
        }
    }

    open class CommonViewHolder(open val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    class TitleViewHolder(override val binding: LayoutTitleBinding) : CommonViewHolder(binding)
    class MenuViewHolder(override val binding: LayoutMenu1Binding) : CommonViewHolder(binding)

    companion object {
        val diffUtil by lazy {
            object : DiffUtil.ItemCallback<MenuModel>() {
                override fun areItemsTheSame(oldItem: MenuModel, newItem: MenuModel) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: MenuModel, newItem: MenuModel) =
                    oldItem == newItem
            }
        }
    }
}
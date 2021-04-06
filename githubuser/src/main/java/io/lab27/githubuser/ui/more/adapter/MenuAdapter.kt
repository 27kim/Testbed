package io.lab27.githubuser.ui.more.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import io.lab27.githubuser.datasource.remote.MenuItem
import kotlinx.android.synthetic.main.layout_horizontal.view.*

class MenuAdapter() : BaseAdapter() {
    private var itemList = listOf<MenuItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemWidth: Int = parent.width / itemList.size
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_horizontal, parent, false)
        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.width = itemWidth
        view.layoutParams = layoutParams
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = if (itemList.isNotEmpty()) itemList.size else 0

    fun setList(list: List<MenuItem>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        val item = itemList[position]
        when (holder) {
            is ViewHolder -> holder.bind(item)
            else -> throw IllegalArgumentException("No viewholder to show this data, did you forgot to add it to the onBindViewHolder?")
        }
    }

    inner class ViewHolder(itemView: View) : BaseConcatHolder<MenuItem>(itemView) {
        override fun bind(item: MenuItem) {
            itemView.menuItem.text = item.title
        }
    }
}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T, position: Int)
}
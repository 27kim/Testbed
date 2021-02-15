package io.lab27.githubuser.ui.more.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.R
import kotlinx.android.synthetic.main.layout_concat_row.view.*

class HorizontalAdapter(private val context: Context, private val baseAdapter: BaseAdapter, private val layoutResId : Int) :
    RecyclerView.Adapter<BaseConcatHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_concat_row,parent,false)
        return ConcatViewHolder(view)
    }

    override fun getItemCount(): Int  = 1

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when(holder){
            is ConcatViewHolder -> holder.bind(baseAdapter)
            else -> throw IllegalArgumentException("No viewholder to show this data, did you forgot to add it to the onBindViewHolder?")
        }
    }

    override fun getItemViewType(position: Int): Int = layoutResId

    inner class ConcatViewHolder(itemView: View): BaseConcatHolder<BaseAdapter>(itemView){
        override fun bind(adapter: BaseAdapter) {
            itemView.rv_horizontal.adapter = adapter
        }
    }
}

abstract class BaseConcatHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(adapter: T)
}

abstract class BaseAdapter : RecyclerView.Adapter<BaseConcatHolder<*>>()
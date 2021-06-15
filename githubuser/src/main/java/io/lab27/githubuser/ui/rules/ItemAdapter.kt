package io.lab27.githubuser.ui.rules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.databinding.ItemRecyclerviewTestBinding

class ItemAdapter : ListAdapter<RvTest, ItemAdapter.ViewHolder>(RvTest.diffUtil) {
    var selectedPosition = -1

    var clickListener: ((RvTest) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemRecyclerviewTestBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = getItem(position)
        holder.binding.title.setOnClickListener {
            holder.binding.title.isChecked = !holder.binding.title.isChecked
            clickListener?.let {
                it.invoke(getItem(position))
                notifyItemChanged(position)
                if (selectedPosition >= 0) {
                    it.invoke(getItem(selectedPosition))
                    notifyItemChanged(selectedPosition)
                }
                selectedPosition = position
            }
        }
    }

    class ViewHolder(val binding: ItemRecyclerviewTestBinding) :
        RecyclerView.ViewHolder(binding.root)
}


/*package io.lab27.githubuser.ui.rules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.databinding.ItemRecyclerviewTestBinding
import kotlinx.android.synthetic.main.item_recyclerview_test.view.*

class ItemAdapter : ListAdapter<RvTest, ItemAdapter.ViewHolder>(RvTest.diffUtil) {
    private var lastCheckedPosition: Int = 0
    var clickListener: ((RvTest) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemRecyclerviewTestBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = getItem(position)
        holder.binding.title.setOnClickListener {
            it.title.isChecked = !it.title.isChecked
//            clickListener?.let {
//
//            }

        }
    }

    class ViewHolder(val binding: ItemRecyclerviewTestBinding) :
        RecyclerView.ViewHolder(binding.root)
}*/
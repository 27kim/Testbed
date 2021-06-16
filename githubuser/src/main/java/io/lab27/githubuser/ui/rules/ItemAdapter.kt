package io.lab27.githubuser.ui.rules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.githubuser.databinding.ItemRecyclerviewTestBinding

class ItemAdapter(private val clickListener: ((RvTest) -> Unit)) :
    ListAdapter<RvTest, ItemAdapter.ViewHolder>(RvTest.diffUtil) {
    var selectedPosition = -1

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
            getItem(position).isChecked = !getItem(position).isChecked
            notifyItemChanged(position)

            if (position != selectedPosition && selectedPosition >= 0) {
                getItem(selectedPosition).isChecked = !getItem(selectedPosition).isChecked
                notifyItemChanged(selectedPosition)
            }

            selectedPosition = if (position == selectedPosition) {
                -1
            } else {
                position
            }
        }
    }

    class ViewHolder(val binding: ItemRecyclerviewTestBinding) :
        RecyclerView.ViewHolder(binding.root)
}
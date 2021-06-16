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

            if (position !=selectedPosition && selectedPosition >= 0) {
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

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.binding.item = getItem(position)
//
//        holder.binding.title.setOnClickListener {
//            clickListener?.let {
//                Log.i(
//                    "logging",
//                    "current : $position selected : $selectedPosition should change : ${position != selectedPosition}"
//                )
//
//                clickListener.invoke(getItem(position))
//                notifyItemChanged(position)
//
//                position
//                    .takeIf { position != selectedPosition }
//                    ?.apply {
//                        selectedPosition.takeIf { selectedPosition != -1 }?.let {
//                            clickListener.invoke(getItem(selectedPosition))
//                            notifyItemChanged(selectedPosition)
//                        }
//                        selectedPosition = position
//                        Log.i("logging", "selectedPosition chaneged: $selectedPosition")
//                    }
//                    ?: run {
//                        selectedPosition = -1
//                        Log.i("logging", "selectedPosition init: $selectedPosition")
//                    }
//            }
//        }
//    }

    class ViewHolder(val binding: ItemRecyclerviewTestBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
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
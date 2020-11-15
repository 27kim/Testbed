package io.lab27.photogallery.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.photogallery.R
import io.lab27.photogallery.databinding.ItemPhotoBinding
import io.lab27.photogallery.datasource.model.Photo

class PhotoAdapter : ListAdapter<Photo, PhotoAdapter.PhotoVH>(diffCallback) {
    init {
        setHasStableIds(true)
    }

    var tracker: SelectionTracker<Long>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoVH {
        return PhotoVH(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PhotoVH, position: Int) {
        tracker?.let {
            Log.i("adapter", "clicked? ${it.isSelected(position.toLong())}")
            holder.onBind(getItem(position), it.isSelected(position.toLong()))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_photo
    }

    override fun getItemId(position: Int): Long = position.toLong()

    inner class PhotoVH(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }

        fun onBind(photo: Photo, isActivated: Boolean) {
            binding.photoItem = photo
            itemView.isActivated = isActivated
        }
    }

    class PhotoDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as PhotoAdapter.PhotoVH).getItemDetails()
            }
            return null
        }
    }

    companion object {
        val diffCallback by lazy {
            object : DiffUtil.ItemCallback<Photo>() {
                override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                    return oldItem.uri == newItem.uri
                }

                override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}

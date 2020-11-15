package io.lab27.photogallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.lab27.photogallery.R
import io.lab27.photogallery.databinding.ItemPhotoBinding
import io.lab27.photogallery.datasource.model.Photo

class PhotoAdapter : ListAdapter<Photo, PhotoAdapter.PhotoVH>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoVH {
        val inflater = LayoutInflater.from(parent.context)

        return PhotoVH(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PhotoVH, position: Int) {
        holder.binding.photoItem = getItem(position)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_photo
    }

    class PhotoVH(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    companion object{
        val diffCallback by lazy{
            object : DiffUtil.ItemCallback<Photo>(){
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

package io.lab27.githubuser.util

import android.R
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@BindingAdapter("imageUrl")
fun setImageResource(view : ImageView, url : String?){
    url?.let {
        Glide.with(view.context)
            .load(url)
            .listener(requestListener(view))
            .into(view)
    }
}

private fun requestListener(view: ImageView): RequestListener<Drawable> {
    return object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            view.setImageResource(R.drawable.stat_notify_error)
            return true
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            view.setImageDrawable(resource)
            return true
        }
    }
}
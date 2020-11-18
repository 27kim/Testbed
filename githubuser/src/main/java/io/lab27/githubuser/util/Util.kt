package io.lab27.githubuser.util

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.module.GlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.lab27.githubuser.R

@BindingAdapter("imageUrl")
fun setImageResource(view : ImageView, url : String?){
    url?.let {
        Glide.with(view.context)
            .load(url)
            .centerCrop()
            .listener(requestListener(view))
            .into(view)
    }
}

@BindingAdapter("mhImageUrl")
fun setMhImageResource(view : ImageView, url : String?){
    val imgUrl = "https://test.happ.hyundai.com$url"
    L.i("mhImageUrl? $imgUrl")
    url?.let {
        Glide
            .with(view.context)
            .load("$imgUrl")
            .centerCrop()
            .listener(requestListener(view))
            .into(view)
    }
}

@BindingAdapter("imageUri")
fun setUriImageResource(view : ImageView, uri : Uri?){
    uri?.let {
        Glide.with(view.context)
            .load(uri)
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
            view.setImageDrawable(view.context.resources.getDrawable(R.drawable.ic_hyundai))
            L.e("imageUrl exception ${e?.localizedMessage}")
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
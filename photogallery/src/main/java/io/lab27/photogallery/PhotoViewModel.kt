package io.lab27.photogallery

import android.app.Application
import android.content.ContentUris
import android.database.Cursor
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.lab27.photogallery.datasource.model.Photo

class PhotoViewModel(private val appl: Application) : AndroidViewModel(appl) {
    private val _photoList = MutableLiveData<List<Photo>>()
    val photoList: LiveData<List<Photo>>
        get() = _photoList

    fun retrievePhotoList() {
        val resolver = appl.contentResolver
        val cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )

        var temp = cursor?.toArrayList { getPhotosFromCursor(it) }

        _photoList.value = temp

    }

    private fun getPhotosFromCursor(cursor: Cursor): Photo {
        return cursor.let { cursor ->
            val fieldIndex: Int = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val id: Long = cursor.getLong(fieldIndex)
            val imageUri =
                ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            return Photo("", imageUri)
        }
    }
}

fun <T> Cursor.toArrayList(block: (Cursor) -> T): List<T> {
    return arrayListOf<T>().also { list ->
        if (moveToFirst()) {
            do {
                list.add(block.invoke(this))
            } while (moveToNext())
        }
    }
}
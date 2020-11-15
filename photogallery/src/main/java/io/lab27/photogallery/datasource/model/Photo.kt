package io.lab27.photogallery.datasource.model

import android.net.Uri

data class Photo(
    val name : String = "",
    val uri : Uri = Uri.EMPTY
)
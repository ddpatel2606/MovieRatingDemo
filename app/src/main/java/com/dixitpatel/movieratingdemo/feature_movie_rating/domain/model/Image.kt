package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val filePath: String
) : Parcelable
package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val character: String?,
    val department: String?,
    val id: Int,
    val job: String?,
    val knownForDepartment: String?,
    val name: String,
    val profilePath: String?
) : Parcelable
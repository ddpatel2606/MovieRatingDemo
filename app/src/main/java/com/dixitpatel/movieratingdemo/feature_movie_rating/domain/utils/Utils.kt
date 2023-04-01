package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class ImageQuality(val imageBaseUrl: String) {
    LOW("https://image.tmdb.org/t/p/w300"),
    HIGH("https://image.tmdb.org/t/p/w780")
}

@Parcelize
enum class SelectedIntentType : Parcelable {
    LIST, VIDEOS, CAST, SEARCH
}

@Parcelize
enum class SelectedMediaType : Parcelable {
    MOVIE, TV, PERSON
}

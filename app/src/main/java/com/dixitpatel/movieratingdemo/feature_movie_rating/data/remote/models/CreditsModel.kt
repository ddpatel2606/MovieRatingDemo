package com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models


import com.google.gson.annotations.SerializedName

data class CreditsModel(
    @SerializedName("cast")
    val cast: List<PersonModel>,
    @SerializedName("crew")
    val crew: List<PersonModel>,
    @SerializedName("guest_stars")
    val guestStars: List<PersonModel>?
)
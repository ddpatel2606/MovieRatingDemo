package com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models

import com.google.gson.annotations.SerializedName

data class PersonDetailModel(
    @SerializedName("biography")
    val biography: String,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("deathday")
    val deathday: String?,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("place_of_birth")
    val placeOfBirth: String?,
    @SerializedName("profile_path")
    val profilePath: String?
)

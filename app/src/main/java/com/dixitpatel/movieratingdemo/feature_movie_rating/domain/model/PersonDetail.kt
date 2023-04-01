package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model

import android.content.Context
import com.dixitpatel.movieratingdemo.R


data class PersonDetail(
    val biography: String,
    val birthday: String?,
    val deathDay: String?,
    val gender: Int,
    val homepage: String?,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val placeOfBirth: String?,
    val profilePath: String?
) {

    fun getGenderText(context: Context) = when (gender) {
        1 -> context.getString(R.string.gender_female)
        2 -> context.getString(R.string.gender_male)
        3 -> context.getString(R.string.gender_non_binary)
        else -> ""
    }

    companion object {
        val empty = PersonDetail(
            biography = "",
            birthday = null,
            deathDay = null,
            gender = 0,
            homepage = null,
            id = 0,
            knownForDepartment = "",
            name = "",
            placeOfBirth = null,
            profilePath = null,
        )
    }
}
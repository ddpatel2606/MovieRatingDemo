package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.persondetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun PersonalDetailScreen(
    viewModel: PersonDetailsViewModel,
    onBackButtonClicked: () -> Unit
) {
    val response = viewModel.details.collectAsState()
    val uiState = viewModel.uiState.collectAsState()

    if(uiState.value.isSuccess) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            PersonalDetailsInfoWidget(
                imagePath = response.value.profilePath,
                personName = response.value.name,
                biography = response.value.biography,
                birthday = response.value.birthday,
                gender = response.value.getGenderText(context = LocalContext.current),
                knownDepartment = response.value.knownForDepartment,
                placeOfBirth = response.value.placeOfBirth,
                onBackButtonClicked = onBackButtonClicked
            )
        }
    }
}
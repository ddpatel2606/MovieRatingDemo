package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.persondetails

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.ImageQuality

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PersonalDetailsInfoWidget(
    imagePath: String? = null,
    personName: String? = null,
    biography: String? = null,
    birthday: String? = null,
    gender: String? = null,
    knownDepartment: String? = null,
    placeOfBirth: String? = null,
    onBackButtonClicked: () -> Unit = {}
) {
    Column {
        Box {
            GlideImage(
                model = ImageQuality.HIGH.imageBaseUrl.plus(imagePath),
                contentDescription = "image_personal",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 3.0f.div(4.0f)),
                contentScale = ContentScale.Crop
            ){
                it.error(R.drawable.ic_baseline_image_24)
            }
            IconButton(
                onClick = onBackButtonClicked,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp),
                    tint = Color.White
                )
            }
        }
        Column(modifier = Modifier.padding(start = 20.dp, end = 8.dp)) {
            if (!personName.isNullOrBlank()) {
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        personName,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                    )
                }
            }
            if (!placeOfBirth.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    stringResource(id = R.string.place_of_birth),
                    style = MaterialTheme.typography.h1
                )
                Text(
                    placeOfBirth,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            if (!birthday.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    stringResource(id = R.string.birthday),
                    style = MaterialTheme.typography.h1
                )
                Text(
                    birthday,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            if (!knownDepartment.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    stringResource(id = R.string.department),
                    style = MaterialTheme.typography.h1
                )
                Text(
                    knownDepartment,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            if (!gender.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    stringResource(id = R.string.gender),
                    style = MaterialTheme.typography.h1
                )
                Text(
                    gender,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            if (!biography.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    stringResource(id = R.string.biography),
                    style = MaterialTheme.typography.h1
                )
                Text(
                    biography, style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PersonalDetailsInfoWidget() {
    PersonalDetailsInfoWidget(
        imagePath = "https://image.tmdb.org/t/p/w780/rRdru6REr9i3WIHv2mntpcgxnoY.jpg",
        personName = "Star Wars",
        biography = "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
        birthday = "2021-08-15",
        gender = "Male",
        knownDepartment = "Acting",
        placeOfBirth = "India"
    )
}
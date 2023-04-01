package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.persondetails

import androidx.lifecycle.viewModelScope
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.PersonDetail
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.GetDetailsUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SelectedMediaType
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.UiState
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(private val getDetails: GetDetailsUseCase) : BaseViewModel() {

    private val _details = MutableStateFlow(PersonDetail.empty)
    val details get() = _details.asStateFlow()

    private fun fetchPersonDetails() {
        viewModelScope.launch {
            getDetails(SelectedMediaType.PERSON, id).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _details.value = response.data as PersonDetail
                        _uiState.value = UiState.successState()
                    }
                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(errorText = response.message)
                    }
                }
            }
        }
    }

    fun initRequest(personId: Int) {
        id = personId
        fetchPersonDetails()
    }
}
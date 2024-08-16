package com.id.shuttershop.ui.screen.transaction_detail.rating

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.rating.IRatingRepository
import com.id.domain.utils.resource.onError
import com.id.domain.utils.resource.onSuccess
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.handleUpdateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 15/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@HiltViewModel
class TransactionRatingViewModel @Inject constructor(
    private val ratingRepository: IRatingRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val message = savedStateHandle.getStateFlow(MESSAGE_VALUE, "")
    val rating = savedStateHandle.getStateFlow<Int?>(RATING_VALUE, null)
    val review = savedStateHandle.getStateFlow(REVIEW_VALUE, "")

    private val _reviewState = MutableStateFlow<UiState<Boolean>>(UiState.Initiate)
    val reviewState = _reviewState.asStateFlow()


    fun sendRating(invoiceId: String, rating: Int, review: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _reviewState.run {
                handleUpdateUiState(UiState.Loading)
                val response = ratingRepository.insertRating(
                    invoiceId = invoiceId,
                    rating = rating,
                    review = review
                )
                response.onSuccess {
                    handleUpdateUiState(UiState.Success(it))
                }.onError { handleUpdateUiState(UiState.Error(it)) }
            }
        }
    }

    fun updateRating(value: Int?) {
        savedStateHandle[RATING_VALUE] = value
    }

    fun updateReview(value: String) {
        savedStateHandle[REVIEW_VALUE] = value
    }

    fun updateMessage(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            savedStateHandle[MESSAGE_VALUE] = value
            delay(1_000)
            savedStateHandle[MESSAGE_VALUE] = ""
        }
    }


    companion object {
        private const val MESSAGE_VALUE = "messageValue"
        private const val RATING_VALUE = "ratingValue"
        private const val REVIEW_VALUE = "reviewValue"
    }
}
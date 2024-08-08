package com.id.shuttershop.ui.screen.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.product.IProductRepository
import com.id.domain.product.ProductModel
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.handleUpdateUiState
import com.id.shuttershop.utils.onError
import com.id.shuttershop.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: IProductRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val searchValue = savedStateHandle.getStateFlow(SEARCH, "")

    private val _searchState = MutableStateFlow<UiState<List<ProductModel>>>(UiState.Initiate)
    val searchState = _searchState.asStateFlow()

    fun fetchSearch(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            with(_searchState) {
                handleUpdateUiState(UiState.Loading)
                if (query.isNotEmpty()) {
                    val response = productRepository.searchProduct(query)
                    response.onSuccess {
                        handleUpdateUiState(UiState.Success(it))
                    }.onError {
                        handleUpdateUiState(UiState.Error(it))
                    }
                } else {
                    handleUpdateUiState(UiState.Initiate)
                }
            }
        }
    }

    fun onSearchValueChange(value: String) {
        savedStateHandle[SEARCH] = value
    }

    companion object {
        private const val SEARCH = "searchValue"
    }
}

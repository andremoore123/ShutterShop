package com.id.shuttershop.ui.screen.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.id.domain.product.IProductRepository
import com.id.domain.product.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    val messageValue = savedStateHandle.getStateFlow(MESSAGE, "")

    private val _searchData = MutableStateFlow<PagingData<ProductModel>>(PagingData.empty())
    val searchData = _searchData.asStateFlow()

    fun setMessageValue(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            savedStateHandle[MESSAGE] = value
            delay(5_000)
            savedStateHandle[MESSAGE] = ""
        }
    }

    fun fetchSearch(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query.isNotEmpty()) {
                productRepository.searchProduct(query = query).cachedIn(viewModelScope).collect {
                    _searchData.emit(it)
                }
            } else {
                _searchData.emit(PagingData.empty())
            }
        }
    }

    fun onSearchValueChange(value: String) {
        savedStateHandle[SEARCH] = value
    }

    companion object {
        private const val SEARCH = "searchValue"
        private const val MESSAGE = "messageValue"
    }
}

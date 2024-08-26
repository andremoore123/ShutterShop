package com.id.shuttershop.ui.screen.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.id.domain.product.IProductRepository
import com.id.shuttershop.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: IProductRepository,
    private val savedStateHandle: SavedStateHandle,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    val searchValue = savedStateHandle.getStateFlow(SEARCH, "")
    val messageValue = savedStateHandle.getStateFlow(MESSAGE, "")

    @OptIn(FlowPreview::class)
    val searchData = searchValue.debounce(500).flatMapLatest {
        if (it.isNotEmpty()) {
            productRepository.searchProduct(it).cachedIn(viewModelScope)
        } else {
            flowOf(PagingData.empty())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PagingData.empty())

    fun setMessageValue(value: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            savedStateHandle[MESSAGE] = value
            delay(5_000)
            savedStateHandle[MESSAGE] = ""
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

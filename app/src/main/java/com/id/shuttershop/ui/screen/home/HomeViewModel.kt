package com.id.shuttershop.ui.screen.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.product.IProductRepository
import com.id.domain.product.ProductModel
import com.id.domain.session.ISessionRepository
import com.id.domain.session.UserModel
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.handleUpdateUiState
import com.id.shuttershop.utils.onError
import com.id.shuttershop.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: IProductRepository,
    private val sessionRepository: ISessionRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _productsUiState = MutableStateFlow<UiState<List<ProductModel>>>(UiState.Initiate)
    val productUiState = _productsUiState.asStateFlow()

    private val _userData = MutableStateFlow<UserModel>(UserModel.emptyModel)
    val userData = _userData.asStateFlow()

    val isColumnLayout = savedStateHandle.getStateFlow(
        LAYOUT_TYPE,
        COLUMN_LAYOUT
    )

    fun fetchUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = sessionRepository.fetchUserData()
            _userData.getAndUpdate {
                response
            }
        }
    }

    fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            with(_productsUiState) {
                handleUpdateUiState(UiState.Loading)
                val response = productRepository.fetchProducts()
                response.onSuccess {
                    handleUpdateUiState(UiState.Success(it))
                }.onError {
                    handleUpdateUiState(UiState.Error(it))
                }
            }
        }
    }

    fun setLayoutType(currentLayout: String) {
        if (currentLayout == GRID_LAYOUT) {
            savedStateHandle[LAYOUT_TYPE] = COLUMN_LAYOUT
        } else {
            savedStateHandle[LAYOUT_TYPE] = GRID_LAYOUT
        }
    }

    companion object {
        const val GRID_LAYOUT = "gridLayout"
        const val COLUMN_LAYOUT = "columnLayout"
        private const val LAYOUT_TYPE = "layoutType"
    }
}
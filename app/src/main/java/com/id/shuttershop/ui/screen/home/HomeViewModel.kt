package com.id.shuttershop.ui.screen.home

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.product.IProductRepository
import com.id.domain.product.ProductFilterParams
import com.id.domain.product.ProductModel
import com.id.domain.session.ISessionRepository
import com.id.domain.session.UserModel
import com.id.shuttershop.utils.analytics.AnalyticsConstants.EVENT_HOME_CART
import com.id.shuttershop.utils.analytics.AnalyticsConstants.EVENT_HOME_LAYOUT
import com.id.shuttershop.utils.analytics.AnalyticsConstants.EVENT_HOME_NOTIFICATION
import com.id.shuttershop.utils.analytics.AnalyticsConstants.EVENT_HOME_PRODUCT_DETAIL
import com.id.shuttershop.utils.analytics.AnalyticsConstants.EVENT_HOME_SEARCH
import com.id.shuttershop.utils.analytics.AnalyticsConstants.PARAM_LAYOUT
import com.id.shuttershop.utils.analytics.AnalyticsConstants.PARAM_SCREEN_NAME
import com.id.shuttershop.utils.analytics.AnalyticsConstants.PRODUCT_NAME
import com.id.shuttershop.utils.analytics.ScreenConstants.SCREEN_HOME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private val analyticRepository: IAnalyticRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _products = MutableStateFlow<PagingData<ProductModel>>(PagingData.empty())
    val products = _products.asStateFlow()

    private val _productFilter = MutableStateFlow(ProductFilterParams())
    val productFilter = _productFilter.asStateFlow()

    private val _userData = MutableStateFlow(UserModel.emptyModel)
    val userData = _userData.asStateFlow()

    val message = savedStateHandle.getStateFlow(MESSAGE_VALUE, "")

    val isBottomShowValue = savedStateHandle.getStateFlow(IS_SHEET_SHOW_VALUE, false)

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
    fun modifySheetValue(value: Boolean) {
        savedStateHandle[IS_SHEET_SHOW_VALUE] = value
    }

    fun setMessage(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            savedStateHandle[MESSAGE_VALUE] = value
            delay(5_00)
            savedStateHandle[MESSAGE_VALUE] = ""
        }
    }

    fun onFilterChange(filterParams: ProductFilterParams) {
        _productFilter.getAndUpdate { filterParams }
    }

    fun fetchProducts(params: ProductFilterParams) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.fetchProducts(params).cachedIn(viewModelScope).collect {
                _products.emit(it)
            }
        }
    }

    fun setLayoutType(currentLayout: String) {
        logHomeLayout(currentLayout)
        if (currentLayout == GRID_LAYOUT) {
            savedStateHandle[LAYOUT_TYPE] = COLUMN_LAYOUT
        } else {
            savedStateHandle[LAYOUT_TYPE] = GRID_LAYOUT
        }
    }

    fun logSearchButton() {
        val params = Bundle().apply {
            putString(PARAM_SCREEN_NAME, SCREEN_HOME)
        }
        analyticRepository.logEvent(EVENT_HOME_NOTIFICATION, params)
    }

    fun logNotificationButton() {
        val params = Bundle().apply {
            putString(PARAM_SCREEN_NAME, SCREEN_HOME)
        }
        analyticRepository.logEvent(EVENT_HOME_SEARCH, params)
    }

    fun logCartButton() {
        val params = Bundle().apply {
            putString(PARAM_SCREEN_NAME, SCREEN_HOME)
        }
        analyticRepository.logEvent(EVENT_HOME_CART, params)
    }

    fun logHomeDetailProduct(productName: String) {
        val params = Bundle().apply {
            putString(PARAM_SCREEN_NAME, SCREEN_HOME)
            putString(PRODUCT_NAME, productName)
        }
        analyticRepository.logEvent(EVENT_HOME_PRODUCT_DETAIL, params)
    }

    private fun logHomeLayout(layoutName: String) {
        val params = Bundle().apply {
            putString(PARAM_SCREEN_NAME, SCREEN_HOME)
            putString(PARAM_LAYOUT, layoutName)
        }
        analyticRepository.logEvent(EVENT_HOME_LAYOUT, params)
    }

    companion object {
        const val GRID_LAYOUT = "gridLayout"
        const val COLUMN_LAYOUT = "columnLayout"
        private const val LAYOUT_TYPE = "layoutType"
        private const val IS_SHEET_SHOW_VALUE = "isSheetShow"
        private const val MESSAGE_VALUE = "messageValue"
    }
}
package com.id.data.product

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.id.data.product.response.ProductDataResponse
import com.id.domain.product.ProductFilterParams

/**
 * Created by: andre.
 * Date: 12/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class ProductPagingSource(
    private val apiService: ProductApiService,
    private val parameter: ProductFilterParams,
    private val productQuery: String = "",
) : PagingSource<Int, ProductDataResponse>() {
    override fun getRefreshKey(state: PagingState<Int, ProductDataResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDataResponse> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiService.fetchProducts(
                searchQuery = productQuery,
                brand = parameter.productCategory,
                lowestPrice = parameter.lowestPrice,
                sortType = parameter.sortBy,
                limit = params.loadSize,
                page = nextPageNumber
            )
            val currentPage = response.data?.pageIndex ?: 0
            val totalPages = response.data?.totalPages ?: 1

            LoadResult.Page(
                data = response.data?.items ?: emptyList(),
                prevKey = if (currentPage > 1) currentPage - 1 else null,
                nextKey = if (currentPage < totalPages) currentPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

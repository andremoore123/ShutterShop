package com.id.data.product

import com.id.data.network.ApiResponse
import com.id.data.product.response.ProductDetailResponse
import com.id.data.product.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by: andre.
 * Date: 12/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface ProductApiService {
    @POST("products")
    suspend fun fetchProducts(
        @Query("search") searchQuery: String?,
        @Query("brand") brand: String?,
        @Query("lowest") lowestPrice: String?,
        @Query("highest") highestPrice: String?,
        @Query("sort") sortType: String?,
        @Query("limit") limit: Int?,
        @Query("page") page: Int?
    ): ApiResponse<ProductResponse>

    @GET("products/{id}")
    suspend fun fetchProductDetail(
        @Path("id") productId: String
    ): ApiResponse<ProductDetailResponse>
}

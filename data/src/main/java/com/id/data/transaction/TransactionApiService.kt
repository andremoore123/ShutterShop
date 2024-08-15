package com.id.data.transaction

import com.id.data.network.ApiResponse
import com.id.data.transaction.request.CheckoutRequest
import com.id.data.transaction.response.CheckoutResponse
import com.id.data.transaction.response.TransactionsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface TransactionApiService {
    @POST("fulfillment")
    suspend fun checkoutItems(@Body checkoutRequest: CheckoutRequest): ApiResponse<CheckoutResponse>

    @GET("transaction")
    suspend fun fetchTransactions(): ApiResponse<List<TransactionsResponse>>
}
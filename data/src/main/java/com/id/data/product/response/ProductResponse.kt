package com.id.data.product.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @field:SerializedName("pageIndex")
    val pageIndex: Int? = null,

    @field:SerializedName("itemsPerPage")
    val itemsPerPage: Int? = null,

    @field:SerializedName("currentItemCount")
    val currentItemCount: Int? = null,

    @field:SerializedName("totalPages")
    val totalPages: Int? = null,

    @field:SerializedName("items")
    val items: List<ProductDataResponse>? = null
)


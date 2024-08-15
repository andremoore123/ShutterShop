package com.id.data.transaction.response

import com.google.gson.annotations.SerializedName
import com.id.domain.transaction.ItemStatus
import com.id.domain.transaction.TransactionModel
import com.id.domain.transaction.TransactionStatus

data class TransactionsResponse(

    @field:SerializedName("date")
	val date: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("total")
	val total: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("invoiceId")
	val invoiceId: String? = null,

	@field:SerializedName("payment")
	val payment: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

    @field:SerializedName("items")
    val items: List<TransactionItemResponse>? = null,

    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("review")
    val review: String? = null,

    @field:SerializedName("rating")
    val rating: Int? = null
)

fun TransactionsResponse.mapToModel() = TransactionModel(
    itemName = "${name.orEmpty()} ${items?.firstOrNull()?.variantName}",
    itemImageUrl = image ?: "",
    itemTotal = determineTotalItem(items ?: listOf()),
    transactionTotal = total ?: 0,
    transactionStatus = if (status == true) TransactionStatus.SUCCESS else TransactionStatus.FAILED,
    transactionDate = date ?: "",
    itemStatus = determineItemStatus(items ?: listOf())
)

internal fun determineTotalItem(data: List<TransactionItemResponse>): Int {
    return if (data.size == 1) {
        data.firstOrNull()?.quantity ?: 0
    } else {
        data.size
    }
}

internal fun determineItemStatus(data: List<TransactionItemResponse>): ItemStatus {
    return if (data.size == 1 || data.isEmpty()) {
        ItemStatus.ONE_TYPE_ITEM
    } else {
        ItemStatus.MORE_THAN_ONE_TYPE_ITEM
    }
}
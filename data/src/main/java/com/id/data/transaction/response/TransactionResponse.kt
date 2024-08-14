package com.id.data.transaction.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("invoiceId")
	val invoiceId: String? = null,

	@field:SerializedName("payment")
	val payment: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

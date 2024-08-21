package com.id.shuttershop.ui.screen.rating

/**
 * Created by: andre.
 * Date: 15/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class TransactionRatingEvent(
    val onRatingChange: (Int) -> Unit,
    val onReviewChange: (String) -> Unit,
    val onDoneClick: () -> Unit,
    val onBackToHomeClick: () -> Unit,
)

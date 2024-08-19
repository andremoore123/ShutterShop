package com.id.shuttershop.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by: andre.
 * Date: 19/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class DispatcherProvider(
    val computing: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val main: CoroutineDispatcher
) {
    constructor() : this(
        computing = Dispatchers.Default,
        io = Dispatchers.IO,
        main = Dispatchers.Main
    )
}

package com.id.data.payment

import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.gson.Gson
import com.id.data.R
import com.id.domain.payment.IPaymentRepository
import com.id.domain.payment.PaymentModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class PaymentRepository @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : IPaymentRepository {

    override fun fetchPaymentMethods(): Flow<List<PaymentModel>> = callbackFlow {
        val onUpdateConfig: () -> Unit = {
            remoteConfig.run {
                setDefaultsAsync(R.xml.remote_config_defaults)
                fetchAndActivate().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        try {
                            val paymentList = remoteConfig.getString("payment_methods")
                            val paymentResponse =
                                Gson().fromJson(paymentList, PaymentResponse::class.java)
                            val mappedList = mutableListOf<PaymentModel>()
                            paymentResponse.data?.forEach { data ->
                                data.item?.forEach { model ->
                                    mappedList.add(model.toModel(data.title ?: ""))
                                }
                            }
                            trySend(mappedList).isSuccess
                        } catch (e: Exception) {
                            close(e)
                        }
                    } else {
                        trySend(listOf()).isFailure
                    }
                }.addOnFailureListener {
                    close(it)
                }
            }
        }

        val listener = object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                if (configUpdate.updatedKeys.contains("payment_methods")) {
                    onUpdateConfig.invoke()
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                trySend(listOf()).isFailure
                close(error) // Close the flow with an exception on error
            }
        }

        // Register the listener
        remoteConfig.addOnConfigUpdateListener(listener)
        onUpdateConfig.invoke() // Initial call to fetch payment methods

        // Clean up the listener when the flow collection is canceled
        awaitClose {
            remoteConfig.reset()
        }
    }
}

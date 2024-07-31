package com.id.domain.auth

import com.id.domain.ext.ErrorType
import com.id.domain.ext.Resource
import com.id.domain.ext.onSuccess
import com.id.domain.ext.onUnknownError
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 31/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class RegisterUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
) {
    suspend operator fun invoke(name: String, email: String, password: String): Resource<String> {
        val response = authRepository.register(name = name, email = email, password = password)
        var result: Resource<String> = Resource.Initiate
        response.onSuccess {
            result = Resource.Success(it)
        }.onUnknownError {
            result = Resource.Error(ErrorType.UnknownError(it.message.toString()))
        }
        return result
    }
}
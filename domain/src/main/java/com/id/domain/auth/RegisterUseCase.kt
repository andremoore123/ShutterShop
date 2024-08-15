package com.id.domain.auth

import com.id.domain.ext.ErrorType
import com.id.domain.ext.Resource
import com.id.domain.ext.onSuccess
import com.id.domain.ext.onUnknownError
import com.id.domain.session.ISessionRepository
import com.id.domain.session.UserModel
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 31/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class RegisterUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val sessionRepository: ISessionRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): Resource<String> {
        val response = authRepository.register(name = name, email = email, password = password)
        var result: Resource<String> = Resource.Initiate
        response.onSuccess {
            sessionRepository.insertUserToken(it.accessToken, it.refreshToken)
            sessionRepository.setUserData(UserModel(name = name, email = email))
        }.onUnknownError {
            result = Resource.Error(ErrorType.UnknownError(it.message.toString()))
        }
        return result
    }
}
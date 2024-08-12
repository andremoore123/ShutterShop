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
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class LoginUseCase @Inject constructor(
    private val sessionRepository: ISessionRepository,
    private val authRepository: IAuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): Resource<String> {
        val response = authRepository.login(email, password)
        var result: Resource<String> = Resource.Initiate
        response.onSuccess {
            sessionRepository.insertUserToken(it.accessToken, it.refreshToken)
            sessionRepository.setUserData(UserModel(it.userName, email))
        }.onUnknownError {
            result = Resource.Error(ErrorType.UnknownError(it.message.toString()))
        }
        return result
    }
}
package com.id.domain.auth

import com.id.domain.session.ISessionRepository
import com.id.domain.session.UserModel
import com.id.domain.utils.ErrorType
import com.id.domain.utils.network_response.onHttpError
import com.id.domain.utils.network_response.onSuccess
import com.id.domain.utils.network_response.onUnknownError
import com.id.domain.utils.resource.Resource
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
            result = Resource.Success(it.userName)
        }.onHttpError{ code, message ->
            result = Resource.Error(ErrorType.HTTPError(code, message))
        }.onUnknownError {
            result = Resource.Error(ErrorType.UnknownError(it.message.toString()))
        }
        return result
    }
}
package com.id.domain.auth

import com.id.domain.session.ISessionRepository
import com.id.domain.wishlist.IWishlistRepository
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class LogoutUseCase @Inject constructor(
    private val sessionRepository: ISessionRepository,
    private val wishlistRepository: IWishlistRepository
) {
    suspend operator fun invoke() {
        sessionRepository.clearUserSession()
        wishlistRepository.clearDatabase()
    }
}
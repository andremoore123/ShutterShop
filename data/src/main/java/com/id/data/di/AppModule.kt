package com.id.data.di

import com.id.data.analytic.AnalyticRepository
import com.id.data.auth.AuthRepository
import com.id.data.cart.CartRepository
import com.id.data.history.HistoryRepository
import com.id.data.preference.PreferenceRepository
import com.id.data.product.ProductRepository
import com.id.data.rating.RatingRepository
import com.id.data.session.SessionRepository
import com.id.data.transaction.TransactionRepository
import com.id.data.wishlist.WishlistRepository
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.auth.IAuthRepository
import com.id.domain.cart.ICartRepository
import com.id.domain.history.IHistoryRepository
import com.id.domain.preference.IPreferenceRepository
import com.id.domain.product.IProductRepository
import com.id.domain.rating.IRatingRepository
import com.id.domain.session.ISessionRepository
import com.id.domain.transaction.ITransactionRepository
import com.id.domain.wishlist.IWishlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    abstract fun providePreferenceRepository(repository: PreferenceRepository): IPreferenceRepository

    @Binds
    abstract fun provideSessionRepository(repository: SessionRepository): ISessionRepository

    @Binds
    abstract fun provideAuthRepository(repository: AuthRepository): IAuthRepository

    @Binds
    abstract fun provideAnalyticRepository(repository: AnalyticRepository): IAnalyticRepository

    @Binds
    abstract fun provideWishlistRepository(repository: WishlistRepository): IWishlistRepository

    @Binds
    abstract fun provideTransactionRepository(repository: TransactionRepository): ITransactionRepository

    @Binds
    abstract fun provideProductRepository(repository: ProductRepository): IProductRepository

    @Binds
    abstract fun provideHistoryRepository(repository: HistoryRepository): IHistoryRepository

    @Binds
    abstract fun provideCartRepository(repository: CartRepository): ICartRepository

    @Binds
    abstract fun provideRatingRepository(repository: RatingRepository): IRatingRepository
}
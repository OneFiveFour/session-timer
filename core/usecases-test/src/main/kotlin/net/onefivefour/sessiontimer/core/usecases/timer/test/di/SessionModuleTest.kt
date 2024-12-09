package net.onefivefour.sessiontimer.core.usecases.timer.test.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockk.mockk
import net.onefivefour.sessiontimer.core.usecases.api.session.DeleteSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.GetAllSessionsUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.NewSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.SetSessionTitleUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionProvidesModule {

    @Provides
    @Singleton
    fun provideDeleteSessionUseCase(): DeleteSessionUseCase = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideGetAllSessionsUseCase(): GetAllSessionsUseCase = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideGetSessionUseCase(): GetSessionUseCase = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideNewSessionUseCase(): NewSessionUseCase = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideSetSessionTitleUseCase(): SetSessionTitleUseCase = mockk(relaxed = true)

}
package net.onefivefour.sessiontimer.core.usecases.timer.test.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import io.mockk.mockk
import net.onefivefour.sessiontimer.core.usecases.api.timer.GetTimerStatusUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.PauseTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.ResetTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.StartTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.test.GetTimerStatusUseCaseFake
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TimerBindsModule {

    @Binds
    @Singleton
    fun bindGetTimerStatusUseCase(
        getTimerStatusUseCaseImpl: GetTimerStatusUseCaseFake
    ): GetTimerStatusUseCase

}

@Module
@InstallIn(SingletonComponent::class)
object TestUseCaseModule {

    @Provides
    @Singleton
    fun provideStartTimerUseCase(): StartTimerUseCase = mockk(relaxed = true)

    @Provides
    @Singleton
    fun providePauseTimerUseCase(): PauseTimerUseCase = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideResetTimerUseCase(): ResetTimerUseCase = mockk(relaxed = true)

}
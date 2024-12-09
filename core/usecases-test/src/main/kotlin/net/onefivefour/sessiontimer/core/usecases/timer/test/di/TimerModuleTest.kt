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

@Module
@InstallIn(ViewModelComponent::class)
interface TimerBindsModule {

    @Binds
    @ViewModelScoped
    fun bindGetTimerStatusUseCase(
        getTimerStatusUseCaseImpl: GetTimerStatusUseCaseFake
    ): GetTimerStatusUseCase

}

@Module
@InstallIn(ViewModelComponent::class)
object TestUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideStartTimerUseCase(): StartTimerUseCase = mockk(relaxed = true)

    @Provides
    @ViewModelScoped
    fun providePauseTimerUseCase(): PauseTimerUseCase = mockk(relaxed = true)

    @Provides
    @ViewModelScoped
    fun provideResetTimerUseCase(): ResetTimerUseCase = mockk(relaxed = true)

}
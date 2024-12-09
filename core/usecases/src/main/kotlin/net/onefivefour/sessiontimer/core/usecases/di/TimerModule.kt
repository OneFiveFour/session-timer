package net.onefivefour.sessiontimer.core.usecases.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.usecases.timer.GetTimerStatusUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.api.timer.GetTimerStatusUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.PauseTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.ResetTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.StartTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.PauseTimerUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.timer.ResetTimerUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.timer.StartTimerUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
internal interface TimerModule {

    @Binds
    @ViewModelScoped
    fun bindGetTimerStatusUseCase(
        impl: GetTimerStatusUseCaseImpl
    ): GetTimerStatusUseCase

    @Binds
    @ViewModelScoped
    fun bindPauseTimerUseCase(
        impl: PauseTimerUseCaseImpl
    ): PauseTimerUseCase

    @Binds
    @ViewModelScoped
    fun bindResetTimerUseCase(
        impl: ResetTimerUseCaseImpl
    ): ResetTimerUseCase

    @Binds
    @ViewModelScoped
    fun bindStartTimerUseCase(
        impl: StartTimerUseCaseImpl
    ): StartTimerUseCase

}
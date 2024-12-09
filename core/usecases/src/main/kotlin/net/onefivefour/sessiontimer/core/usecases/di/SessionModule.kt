package net.onefivefour.sessiontimer.core.usecases.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.usecases.api.session.DeleteSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.GetAllSessionsUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.NewSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.SetSessionTitleUseCase
import net.onefivefour.sessiontimer.core.usecases.session.DeleteSessionUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.session.GetAllSessionsUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.session.GetSessionUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.session.NewSessionUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.session.SetSessionTitleUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
internal interface SessionModule {

    @Binds
    @ViewModelScoped
    fun bindDeleteSessionUseCase(impl: DeleteSessionUseCaseImpl): DeleteSessionUseCase

    @Binds
    @ViewModelScoped
    fun bindGetAllSessionsUseCase(impl: GetAllSessionsUseCaseImpl): GetAllSessionsUseCase

    @Binds
    @ViewModelScoped
    fun bindGetSessionUseCase(impl: GetSessionUseCaseImpl): GetSessionUseCase

    @Binds
    @ViewModelScoped
    fun bindNewSessionUseCase(impl: NewSessionUseCaseImpl): NewSessionUseCase

    @Binds
    @ViewModelScoped
    fun bindSetSessionTitleUseCase(impl: SetSessionTitleUseCaseImpl): SetSessionTitleUseCase
}
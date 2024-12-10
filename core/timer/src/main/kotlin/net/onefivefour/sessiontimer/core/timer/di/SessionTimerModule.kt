package net.onefivefour.sessiontimer.core.timer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import net.onefivefour.sessiontimer.core.timer.SessionTimerImpl
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer

@Module
@InstallIn(ViewModelComponent::class)
internal interface SessionTimerModule {

    @Binds
    @ViewModelScoped
    fun bindsSessionTimer(impl: SessionTimerImpl): SessionTimer
}

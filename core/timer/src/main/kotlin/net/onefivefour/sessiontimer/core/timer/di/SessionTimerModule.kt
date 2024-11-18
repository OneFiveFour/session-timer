package net.onefivefour.sessiontimer.core.timer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import net.onefivefour.sessiontimer.core.timer.SessionTimerImpl
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer

@Module
@InstallIn(SingletonComponent::class)
internal interface SessionTimerModule {

    @Binds
    @Singleton
    fun bindsSessionTimer(impl: SessionTimerImpl): SessionTimer
}

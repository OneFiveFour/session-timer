package net.onefivefour.sessiontimer.feature.sessionplayer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerScreen
import net.onefivefour.sessiontimer.feature.sessionplayer.ui.SessionPlayerScreenImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface SessionPlayerModule {

    @Binds
    @Singleton
    fun bindsSessionPlayerScreen(impl: SessionPlayerScreenImpl): SessionPlayerScreen
}
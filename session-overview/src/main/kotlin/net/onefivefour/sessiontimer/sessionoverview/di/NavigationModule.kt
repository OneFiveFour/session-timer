package net.onefivefour.sessiontimer.sessionoverview.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.onefivefour.sessiontimer.sessionoverview.api.SessionOverviewNavigationApi
import net.onefivefour.sessiontimer.sessionoverview.navigation.SessionOverviewNavigation


@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindsSessionOverviewNavigation(navigation: SessionOverviewNavigation) : SessionOverviewNavigationApi

}
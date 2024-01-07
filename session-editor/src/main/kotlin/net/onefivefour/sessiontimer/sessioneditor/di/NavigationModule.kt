package net.onefivefour.sessiontimer.sessioneditor.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.onefivefour.sessiontimer.sessioneditor.api.SessionEditorNavigationApi
import net.onefivefour.sessiontimer.sessioneditor.navigation.SessionEditorNavigation

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    
    @Binds
    fun bindsSessionOverviewNavigation(navigation: SessionEditorNavigation) : SessionEditorNavigationApi
    
}
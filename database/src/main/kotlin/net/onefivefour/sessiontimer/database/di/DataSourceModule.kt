package net.onefivefour.sessiontimer.database.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.onefivefour.sessiontimer.database.BlipDataSource
import net.onefivefour.sessiontimer.database.BlipDataSourceImpl
import net.onefivefour.sessiontimer.database.BlopDataSource
import net.onefivefour.sessiontimer.database.BlopDataSourceImpl
import net.onefivefour.sessiontimer.database.SessionDataSource
import net.onefivefour.sessiontimer.database.SessionDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    fun bindsBlipDataSource(blipDataSource: BlipDataSourceImpl) : BlipDataSource

    @Binds
    fun bindsBlopDataSource(blopDataSource: BlopDataSourceImpl) : BlopDataSource

    @Binds
    fun bindsSessionDataSource(sessionDataSource: SessionDataSourceImpl) : SessionDataSource
}
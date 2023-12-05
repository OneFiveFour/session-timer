package net.onefivefour.sessiontimer.database.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.onefivefour.sessiontimer.database.TaskDataSource
import net.onefivefour.sessiontimer.database.TaskDataSourceImpl
import net.onefivefour.sessiontimer.database.TaskGroupDataSource
import net.onefivefour.sessiontimer.database.TaskGroupDataSourceImpl
import net.onefivefour.sessiontimer.database.SessionDataSource
import net.onefivefour.sessiontimer.database.SessionDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    fun bindsTaskDataSource(taskDataSource: TaskDataSourceImpl) : TaskDataSource

    @Binds
    fun bindsTaskGroupDataSource(taskGroupDataSource: TaskGroupDataSourceImpl) : TaskGroupDataSource

    @Binds
    fun bindsSessionDataSource(sessionDataSource: SessionDataSourceImpl) : SessionDataSource
}
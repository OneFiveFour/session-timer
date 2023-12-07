package net.onefivefour.sessiontimer.database.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.onefivefour.sessiontimer.database.data.TaskDataSource
import net.onefivefour.sessiontimer.database.data.TaskDataSourceImpl
import net.onefivefour.sessiontimer.database.data.TaskGroupDataSource
import net.onefivefour.sessiontimer.database.data.TaskGroupDataSourceImpl
import net.onefivefour.sessiontimer.database.data.SessionDataSource
import net.onefivefour.sessiontimer.database.data.SessionDataSourceImpl

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
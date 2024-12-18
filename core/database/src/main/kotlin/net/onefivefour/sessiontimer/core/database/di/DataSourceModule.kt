package net.onefivefour.sessiontimer.core.database.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.onefivefour.sessiontimer.core.database.data.SessionDataSource
import net.onefivefour.sessiontimer.core.database.data.SessionDataSourceImpl
import net.onefivefour.sessiontimer.core.database.data.TaskDataSource
import net.onefivefour.sessiontimer.core.database.data.TaskDataSourceImpl
import net.onefivefour.sessiontimer.core.database.data.TaskGroupDataSource
import net.onefivefour.sessiontimer.core.database.data.TaskGroupDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    fun bindTaskDataSource(taskDataSource: TaskDataSourceImpl): TaskDataSource

    @Binds
    fun bindTaskGroupDataSource(taskGroupDataSource: TaskGroupDataSourceImpl): TaskGroupDataSource

    @Binds
    fun bindSessionDataSource(sessionDataSource: SessionDataSourceImpl): SessionDataSource
}

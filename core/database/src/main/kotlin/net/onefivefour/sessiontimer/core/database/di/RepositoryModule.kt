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
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.SessionRepositoryImpl
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepositoryImpl
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindSessionRepository(sessionRepository: SessionRepositoryImpl): SessionRepository

    @Binds
    fun bindTaskGroupRepository(taskGroupRepository: TaskGroupRepositoryImpl): TaskGroupRepository

    @Binds
    fun bindTaskRepository(taskRepository: TaskRepositoryImpl): TaskRepository
}

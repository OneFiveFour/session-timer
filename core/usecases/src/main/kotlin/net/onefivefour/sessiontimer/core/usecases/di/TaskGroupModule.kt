package net.onefivefour.sessiontimer.core.usecases.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.DeleteTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.GetTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.NewTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.UpdateTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.taskgroup.DeleteTaskGroupUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.taskgroup.GetTaskGroupUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.taskgroup.NewTaskGroupUseCaseImpl
import net.onefivefour.sessiontimer.core.usecases.taskgroup.UpdateTaskGroupUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
internal interface TaskGroupModule {

    @Binds
    @ViewModelScoped
    fun bindDeleteTaskGroupUseCase(impl: DeleteTaskGroupUseCaseImpl): DeleteTaskGroupUseCase

    @Binds
    @ViewModelScoped
    fun bindGetTaskGroupUseCase(impl: GetTaskGroupUseCaseImpl): GetTaskGroupUseCase

    @Binds
    @ViewModelScoped
    fun bindNewTaskGroupUseCase(impl: NewTaskGroupUseCaseImpl): NewTaskGroupUseCase

    @Binds
    @ViewModelScoped
    fun bindSetTaskGroupTitleUseCase(impl: UpdateTaskGroupUseCaseImpl): UpdateTaskGroupUseCase
}

package net.onefivefour.sessiontimer.core.database.di

import app.cash.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import net.onefivefour.sessiontimer.core.database.Database
import net.onefivefour.sessiontimer.core.database.SessionQueries
import net.onefivefour.sessiontimer.core.database.TaskGroupQueries
import net.onefivefour.sessiontimer.core.database.TaskQueries

@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(driver: SqlDriver): Database {
        return Database(driver)
    }

    @Provides
    @Singleton
    fun provideSessionQueries(database: Database): SessionQueries {
        return database.sessionQueries
    }

    @Provides
    @Singleton
    fun provideTaskGroupQueries(database: Database): TaskGroupQueries {
        return database.taskGroupQueries
    }

    @Provides
    @Singleton
    fun provideTaskQueries(database: Database): TaskQueries {
        return database.taskQueries
    }
}

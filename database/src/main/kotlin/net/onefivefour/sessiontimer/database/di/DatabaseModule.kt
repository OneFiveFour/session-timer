package net.onefivefour.sessiontimer.database.di

import app.cash.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.onefivefour.sessiontimer.database.Database
import net.onefivefour.sessiontimer.database.SessionQueries
import net.onefivefour.sessiontimer.database.TaskGroupQueries
import net.onefivefour.sessiontimer.database.TaskQueries
import javax.inject.Singleton

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

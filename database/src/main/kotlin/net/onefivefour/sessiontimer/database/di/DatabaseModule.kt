package net.onefivefour.sessiontimer.database.di

import app.cash.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.onefivefour.sessiontimer.SessionQueries
import net.onefivefour.sessiontimer.database.Database
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(driver: SqlDriver) : Database {
        return Database(driver)
    }

    @Provides
    @Singleton
    fun provideSessionQueries(database: Database) : SessionQueries {
        return database.sessionQueries
    }

}
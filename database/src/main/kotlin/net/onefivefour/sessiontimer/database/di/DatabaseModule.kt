package net.onefivefour.sessiontimer.database.di

import app.cash.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.onefivefour.sessiontimer.BlipQueries
import net.onefivefour.sessiontimer.BlopQueries
import javax.inject.Singleton
import net.onefivefour.sessiontimer.SessionQueries
import net.onefivefour.sessiontimer.database.Database

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
    fun provideBlopQueries(database: Database): BlopQueries {
        return database.blopQueries
    }

    @Provides
    @Singleton
    fun provideBlipQueries(database: Database): BlipQueries {
        return database.blipQueries
    }
}

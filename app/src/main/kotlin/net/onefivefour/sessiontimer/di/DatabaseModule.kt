package net.onefivefour.sessiontimer.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.onefivefour.sessiontimer.database.Database
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseDriver(@ApplicationContext context : Context) : SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "sessions.db")
    }

}
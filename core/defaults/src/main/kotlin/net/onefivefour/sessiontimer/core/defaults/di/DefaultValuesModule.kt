package net.onefivefour.sessiontimer.core.defaults.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import net.onefivefour.sessiontimer.core.database.domain.DatabaseDefaultValues
import net.onefivefour.sessiontimer.core.defaults.DatabaseDefaultValuesImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface DefaultValuesModule {

    @Binds
    @Singleton
    fun bindDatabaseDefaultValues(impl: DatabaseDefaultValuesImpl): DatabaseDefaultValues
}

package com.example.currencytracking.di

import android.content.Context
import com.example.currencytracking.data.database.FavoriteCurrenciesDao
import com.example.currencytracking.data.database.FavoriteCurrenciesDatabase
import com.example.currencytracking.data.database.SettingsDao
import com.example.currencytracking.data.database.SettingsDatabase
import com.example.currencytracking.domain.mappers.FavoriteCurrenciesMapper
import com.example.currencytracking.domain.mappers.RatesMapper
import com.example.currencytracking.domain.mappers.SettingsMapper
import com.example.currencytracking.domain.formatters.RatesFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideFormatter() = RatesFormatter()

    @Provides
    fun provideRatesMapper() = RatesMapper()

    @Provides
    fun provideFavoriteCurrenciesMapper() = FavoriteCurrenciesMapper()

    @Provides
    fun provideSettingsMapper() = SettingsMapper()

    @Singleton
    @Provides
    fun provideFavoriteCurrenciesDatabase(
        @ApplicationContext appContext: Context
    ): FavoriteCurrenciesDatabase {
        return FavoriteCurrenciesDatabase.getDatabase(appContext)
    }

    @Singleton
    @Provides
    fun provideFavoriteCurrenciesDao(db: FavoriteCurrenciesDatabase): FavoriteCurrenciesDao {
        return db.favoriteCurrenciesDao()
    }

    @Singleton
    @Provides
    fun provideSettingsDatabase(
        @ApplicationContext appContext: Context
    ): SettingsDatabase {
        return SettingsDatabase.getDatabase(appContext)
    }

    @Singleton
    @Provides
    fun provideSettingsDao(db: SettingsDatabase): SettingsDao {
        return db.settingsDao()
    }

}
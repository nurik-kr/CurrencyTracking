package com.example.currencytracking.di

import com.example.currencytracking.data.repositories.FavoriteCurrenciesRepositoryImpl
import com.example.currencytracking.data.repositories.RatesRepositoryImpl
import com.example.currencytracking.data.repositories.SettingsRepositoryImpl
import com.example.currencytracking.domain.repositories.FavoriteCurrenciesRepository
import com.example.currencytracking.domain.repositories.RatesRepository
import com.example.currencytracking.domain.repositories.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoriesModule {

    @Binds
    fun favoriteCurrenciesRepository(
        favoriteCurrenciesRepositoryImpl: FavoriteCurrenciesRepositoryImpl
    ): FavoriteCurrenciesRepository

    @Binds
    fun ratesRepository(ratesRepositoryImpl: RatesRepositoryImpl): RatesRepository

    @Binds
    fun settingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

}
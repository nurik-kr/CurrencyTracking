package com.example.currencytracking.ui.favorites

import androidx.lifecycle.ViewModel
import com.example.currencytracking.data.viewobjects.RatesVo
import com.example.currencytracking.domain.constants.Settings
import com.example.currencytracking.domain.formatters.RatesFormatter
import com.example.currencytracking.domain.models.RatesResponse
import com.example.currencytracking.domain.models.Result
import com.example.currencytracking.domain.usecases.FavoriteCurrencyUseCase
import com.example.currencytracking.domain.usecases.RatesUseCase
import com.example.currencytracking.domain.usecases.SettingUseCase
import com.example.currencytracking.utils.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val ratesUseCase: RatesUseCase,
    private val favoriteCurrencyUseCase: FavoriteCurrencyUseCase,
    private val settingsUseCase: SettingUseCase,
    private val formatter: RatesFormatter
) : ViewModel() {

    //region StateFlow
    private val ratesOrderStateFlow = MutableStateFlow(RatesVo())
    val rates: StateFlow<RatesVo> get() = ratesOrderStateFlow

    private val refreshStatusOrderStateFlow = MutableStateFlow(false)
    val refreshStatus: StateFlow<Boolean> get() = refreshStatusOrderStateFlow

    private val contentIsEmptyStatusOrderStateFlow = MutableStateFlow(true)
    val contentIsEmpty: StateFlow<Boolean> get() = contentIsEmptyStatusOrderStateFlow
    //endregion

    //region SharedFlow
    val error: SharedFlow<String>
        get() = errorSharedFlow.asSharedFlow()
    private val errorSharedFlow = MutableSharedFlow<String>()
    //endregion

    fun getAllFavoriteCurrenciesRates() {
        CoroutineScope(Dispatchers.IO).launch {
            refreshStatusOrderStateFlow.emit(true)
            contentIsEmptyStatusOrderStateFlow.emit(true)
            val allFavoriteCurrencies = favoriteCurrencyUseCase.getAllFavoriteCurrencies()
            if (allFavoriteCurrencies.isNotEmpty()) {
                val ratesResponse = ratesUseCase.getRatesForSpecificCurrencies(
                    specificCurrencies = allFavoriteCurrencies
                )
                when (ratesResponse) {
                    is Result.Success -> handleSuccess(ratesResponse.value)
                    is Result.Failure -> handleError(ratesResponse.cause)
                }
            } else {
                ratesOrderStateFlow.emit(RatesVo())
                contentIsEmptyStatusOrderStateFlow.emit(false)
                refreshStatusOrderStateFlow.emit(false)
            }
        }
    }

    private suspend fun handleSuccess(ratesResponse: RatesResponse) {
        val settings = settingsUseCase.getSettings()
        val isAlphabetSetting = getSetting(Settings.ALPHABET_SORTING_VALUE, settings)
        val isAscendingOrder = getSetting(Settings.ASCENDING_ORDER_VALUE, settings)
        val ratesVo = formatter.formatFavorites(
            ratesResponse = ratesResponse,
            isAlphabetSorting = isAlphabetSetting,
            isAscendingOrder = isAscendingOrder,
        )
        ratesOrderStateFlow.emit(ratesVo)
        refreshStatusOrderStateFlow.emit(false)
    }

    private suspend fun handleError(cause: Throwable) {
        val message = ErrorHandler.getMessageByThrowable(cause)
        errorSharedFlow.emit(message)
    }

    private fun getSetting(parameter: String, settings: Map<String, Boolean>): Boolean {
        val value = settings[parameter]
        return value == null || value
    }

    fun changeFavoriteCurrencies(currency: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentFavoriteCurrencies = favoriteCurrencyUseCase.getByCurrency(currency)
            if (currentFavoriteCurrencies.isNotEmpty()) {
                currentFavoriteCurrencies.forEach {
                    favoriteCurrencyUseCase.delete(it)
                }
            }
            val countFavoriteCurrencies = favoriteCurrencyUseCase.getCountFavoriteCurrencies()
            contentIsEmptyStatusOrderStateFlow.emit(countFavoriteCurrencies != 0)
            getAllFavoriteCurrenciesRates()
        }
    }

    fun deleteAllFavoriteCurrencies() {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteCurrencyUseCase.deleteAllFavoriteCurrencies()
            getAllFavoriteCurrenciesRates()
        }
    }
}
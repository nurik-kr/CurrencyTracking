package com.example.currencytracking.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencytracking.data.dto.FavoriteCurrency
import com.example.currencytracking.domain.models.RatesResponse
import com.example.currencytracking.domain.constants.Settings
import com.example.currencytracking.data.viewobjects.RatesVo
import com.example.currencytracking.domain.formatters.RatesFormatter
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
class PopularViewModel @Inject constructor(
    private val ratesUseCase: RatesUseCase,
    private val favoriteCurrencyUseCase: FavoriteCurrencyUseCase,
    private val settingsUseCase: SettingUseCase,
    private val formatter: RatesFormatter,
) : ViewModel() {

    //region stateFlow
    private val refreshStatusOrderStateFlow = MutableStateFlow(false)
    val refreshStatus get() = refreshStatusOrderStateFlow

    private val ratesOrderStateFlow = MutableStateFlow(RatesVo())
    val rates get() = ratesOrderStateFlow
    //endregion

    //region SharedFlow
    private val errorSharedFlow = MutableSharedFlow<String>()
    val error get() = errorSharedFlow.asSharedFlow()
    //endregion

    // region loadData
    fun loadData(base: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            refreshStatusOrderStateFlow.emit(true)
            when (val ratesResponse = ratesUseCase.getRates(base)) {
                is Result.Success -> handleSuccess(ratesResponse.value)
                is Result.Failure -> handleError(ratesResponse.cause)
            }
        }
    }

    private suspend fun handleSuccess(ratesResponse: RatesResponse) {
        val allFavoriteCurrencies = favoriteCurrencyUseCase.getAllFavoriteCurrencies()
        val settings = settingsUseCase.getSettings()
        val isAlphabetSetting = getSetting(Settings.ALPHABET_SORTING_VALUE, settings)
        val isAscendingOrder = getSetting(Settings.ASCENDING_ORDER_VALUE, settings)
        val ratesVo = formatter.format(
            ratesResponse = ratesResponse,
            favoriteCurrencies = allFavoriteCurrencies,
            isAlphabetSorting = isAlphabetSetting,
            isAscendingOrder = isAscendingOrder
        )
        ratesOrderStateFlow.emit(ratesVo)
        refreshStatusOrderStateFlow.emit(false)
    }

    fun changeFavoriteCurrencies(currency: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentFavoriteCurrencies = favoriteCurrencyUseCase.getByCurrency(currency)
            if (currentFavoriteCurrencies.isNotEmpty()) {
                currentFavoriteCurrencies.forEach {
                    favoriteCurrencyUseCase.delete(it)
                }
            } else {
                val favoriteCurrency = FavoriteCurrency(currency = currency)
                favoriteCurrencyUseCase.insert(favoriteCurrency)
            }
        }
    }

    private suspend fun handleError(cause: Throwable) {
        val message = ErrorHandler.getMessageByThrowable(cause)
        errorSharedFlow.emit(message)
    }

    private fun getSetting(parameter: String, settings: Map<String, Boolean>): Boolean {
        val value = settings[parameter]
        return value == null || value
    }

}
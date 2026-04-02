package jp.co.yumemi.droidtraining

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import jp.co.yumemi.api.UnknownException
import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.droidtraining.ui.state.Weather
import jp.co.yumemi.droidtraining.ui.state.WeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherViewModel @Inject constructor(val weatherApi: YumemiWeather) : ViewModel() {

    private val _weatherState =
            MutableStateFlow(WeatherState(weather = null, showErrorDialog = false))

    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    fun fetchSimpleWeather() {
        viewModelScope.launch {
            val newState =
                    try {
                        val weatherEnum =
                                when (weatherApi.fetchThrowsWeather()) {
                                    "sunny" -> Weather.Sunny
                                    "cloudy" -> Weather.Cloudy
                                    "rainy" -> Weather.Rainy
                                    else -> Weather.Snow
                                }

                        WeatherState(weather = weatherEnum, showErrorDialog = false)
                    } catch (e: UnknownException) {
                        WeatherState(weather = null, showErrorDialog = true)
                    }

            _weatherState.value = newState
        }
    }

    fun dismissErrorDialog() {
        _weatherState.value = _weatherState.value.copy(showErrorDialog = false)
    }
}

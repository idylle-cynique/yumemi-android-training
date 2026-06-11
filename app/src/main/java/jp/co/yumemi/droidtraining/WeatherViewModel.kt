package jp.co.yumemi.droidtraining

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import jp.co.yumemi.api.UnknownException
import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.droidtraining.ui.state.Weather
import jp.co.yumemi.droidtraining.ui.state.WeatherState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherViewModel @Inject constructor(
    val weatherApi: YumemiWeather,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val weatherState: StateFlow<WeatherState> = savedStateHandle.getStateFlow(
        KEY_WEATHER_STATE,
        WeatherState(weather = null, showErrorDialog = false),
    )

    init {
        fetchSimpleWeather()
    }

    fun fetchSimpleWeather() {
        viewModelScope.launch {
            savedStateHandle[KEY_WEATHER_STATE] =
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
        }
    }

    fun dismissErrorDialog() {
        savedStateHandle[KEY_WEATHER_STATE] = weatherState.value.copy(showErrorDialog = false)
    }

    companion object {
        private const val KEY_WEATHER_STATE = "weather_state"
    }
}

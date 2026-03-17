package jp.co.yumemi.droidtraining

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import jp.co.yumemi.api.UnknownException
import jp.co.yumemi.api.YumemiWeather
import jp.co.yumemi.droidtraining.ui.state.Weather
import jp.co.yumemi.droidtraining.ui.state.WeatherState

class WeatherViewModel(val weatherApi: YumemiWeather) : ViewModel() {

    fun fetchSimpleWeather(): WeatherState {
        val weatherInfo: WeatherState = try {
            val weatherEnum = when(weatherApi.fetchThrowsWeather()) {
                "sunny" -> Weather.Sunny
                "cloudy" -> Weather.Cloudy
                "rainy" -> Weather.Rainy
                else -> Weather.Snow
            }

            WeatherState(weather = weatherEnum, showErrorDialog = false)
        } catch (e: UnknownException) {
            WeatherState(weather = null, showErrorDialog = true)
        }

        return weatherInfo
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer<WeatherViewModel> {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Context
                val weatherApi = YumemiWeather(context = application)

                WeatherViewModel(weatherApi = weatherApi)
            }
        }
    }
}


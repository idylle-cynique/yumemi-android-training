package jp.co.yumemi.droidtraining.ui.state

data class WeatherState (
    val weather: Weather?,
    val showErrorDialog: Boolean
)

enum class Weather {
    Sunny, Cloudy, Rainy, Snow
}

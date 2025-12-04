package jp.co.yumemi.droidtraining.ui.state

import androidx.annotation.DrawableRes
import jp.co.yumemi.droidtraining.R

data class WeatherState (
    val weather: Weather?,
    val showErrorDialog: Boolean
)

enum class Weather(@DrawableRes val drawableRes: Int) {
    Sunny(R.drawable.sunny),
    Cloudy(R.drawable.cloudy),
    Rainy(R.drawable.rainy),
    Snow(R.drawable.snow)
}

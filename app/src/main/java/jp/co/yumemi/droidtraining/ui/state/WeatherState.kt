package jp.co.yumemi.droidtraining.ui.state

import android.os.Parcelable
import androidx.annotation.DrawableRes
import jp.co.yumemi.droidtraining.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherState(
    val weather: Weather?,
    val showErrorDialog: Boolean
) : Parcelable

@Parcelize
enum class Weather(@DrawableRes val drawableRes: Int) : Parcelable {
    Sunny(R.drawable.sunny),
    Cloudy(R.drawable.cloudy),
    Rainy(R.drawable.rainy),
    Snow(R.drawable.snow)
}

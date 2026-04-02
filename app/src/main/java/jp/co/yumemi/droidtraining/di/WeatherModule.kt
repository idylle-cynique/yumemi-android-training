package jp.co.yumemi.droidtraining.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import jp.co.yumemi.api.YumemiWeather

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideYumemiWeather(@ApplicationContext context: Context): YumemiWeather =
            YumemiWeather(context = context)
}

package jp.co.yumemi.droidtraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import jp.co.yumemi.droidtraining.ui.state.WeatherState
import jp.co.yumemi.droidtraining.ui.theme.YumemiTheme

class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels { WeatherViewModel.Factory }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YumemiTheme {
                Scaffold(
                        topBar = {
                            TopAppBar(
                                    title = { Text(text = stringResource(id = R.string.app_name)) },
                            )
                        }
                ) { innerPadding ->
                    Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize().padding(innerPadding),
                    ) {
                        Column() {
                            val weatherState by viewModel.weatherState.collectAsState()

                            // アプリ起動時だけ実行
                            LaunchedEffect(Unit) { viewModel.fetchSimpleWeather() }

                            if (weatherState.showErrorDialog) {
                                WeatherAlertDialog(
                                        reloadAction = { viewModel.fetchSimpleWeather() },
                                        cancelAction = { viewModel.dismissErrorDialog() }
                                )
                            } else {
                                WeatherInfo(weatherState)
                            }
                            Spacer(modifier = Modifier.height(80.dp))
                            ActionButtons({ viewModel.fetchSimpleWeather() })
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun WeatherInfo(changedWeather: WeatherState) {
        Column() {
            if (changedWeather.weather != null) {
                Image(
                        painter = painterResource(id = changedWeather.weather.drawableRes),
                        contentDescription = "A Weather Icon",
                        modifier = Modifier.fillMaxWidth(fraction = 0.5f).aspectRatio(1.0f),
                )
            }
            Row(
                    modifier = Modifier.fillMaxWidth(fraction = 0.5f),
            ) {
                Text(
                        text = "10℃",
                        textAlign = TextAlign.Center,
                        color = Color.Blue,
                        modifier = Modifier.weight(1.0f),
                )
                Text(
                        text = "20℃",
                        textAlign = TextAlign.Center,
                        color = Color.Red,
                        modifier = Modifier.weight(1.0f),
                )
            }
        }
    }

    @Composable
    fun ActionButtons(onClick: () -> Unit) {
        Row(modifier = Modifier.fillMaxWidth(fraction = 0.5f)) {
            Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier.weight(1.0f),
                    shape = CutCornerShape(size = 0.dp),
            ) { Text(text = "RELOAD") }
            Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier.weight(1.0f),
                    shape = CutCornerShape(size = 0.dp),
            ) { Text(text = "NEXT") }
        }
    }

    @Composable
    fun WeatherAlertDialog(reloadAction: () -> Unit, cancelAction: () -> Unit) {
        AlertDialog(
                title = { Text(text = "Error") },
                text = { Text(text = "エラーが発生しました。") },
                dismissButton = { Button(onClick = { cancelAction() }) { Text("CANCEL") } },
                onDismissRequest = { cancelAction() },
                confirmButton = { Button(onClick = { reloadAction() }) { Text("RELOAD") } }
        )
    }
}

package jp.co.yumemi.droidtraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.co.yumemi.droidtraining.ui.theme.YumemiTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YumemiTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            },
                        )
                    },
                ) { innerPadding ->
                    Box(
                        //  box→picture, text text
                        //  button, button
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        Column(){
                            Image(
                                painter = painterResource(id = R.drawable.sunny),
                                contentDescription = "A Weather Icon",
                                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                                contentScale = ContentScale.Fit
                            )
                            Row(){
                                Button(
                                    onClick = { }
                                ) {
                                    Text(
                                        text = "Button 1"
                                    )
                                }
                                Button(
                                    onClick = { }
                                ) {
                                    Text(
                                        text = "Button 2"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

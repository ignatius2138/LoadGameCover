package com.example.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.practice.ui.theme.PracticeTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    val viewmodel: GameCoversViewModel = viewModel()
    val state = viewmodel.uiState.collectAsState().value

    LaunchedEffect(text) {
        delay(3000)
        viewmodel.getImageUrl(text)
    }

    when(state) {
        is UiState.Error -> {
            val errorText = state.errorText ?: stringResource(R.string.unknown_error_text)
            Text(
                text = errorText,
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> {
            Column(modifier = modifier) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Enter Game Name") },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                AsyncImage(
                    model = state.url,
                    contentDescription = null
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PracticeTheme {
        MainScreen()
    }
}
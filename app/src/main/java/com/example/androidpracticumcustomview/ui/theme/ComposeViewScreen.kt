package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/*
Задание:
Реализуйте необходимые компоненты.
*/

@Composable
fun ComposeViewScreen() {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ComposeCustomViewGroup(
                modifier = Modifier.padding(innerPadding),
                first = { Text("FirstView") },
                second = { Text("SecondView") }
            )
        }
}
package com.coooldoggy.emotionaloutlet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmotionalOutletApp() {
    Scaffold(
        topBar = {
            AppBar(
                centerContent = { Text("쓰레기♻️") },
                leftContent = { AppBarBackIcon(action = {}) }
            )
        },
    ) { _innerPadding ->
        Box(modifier = Modifier.padding(_innerPadding)) {
            ChatMain()
        }
    }
}


@Composable
fun ChatMain() {
}

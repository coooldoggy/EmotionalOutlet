package com.coooldoggy.emotionaloutlet.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.coooldoggy.emotionaloutlet.EmotionalOutletApp
import com.coooldoggy.emotionaloutlet.MainView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmotionalOutletApp()
        }
    }
}
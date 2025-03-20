package com.example.final_submission_jetpack_compose.ui.screen.about

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboutScreen { finish() }
        }
    }
}




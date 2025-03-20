package com.example.final_submission_jetpack_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.final_submission_jetpack_compose.ui.theme.Final_Submission_Jetpack_ComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Final_Submission_Jetpack_ComposeTheme {
                StoreApp()
            }
        }
    }
}

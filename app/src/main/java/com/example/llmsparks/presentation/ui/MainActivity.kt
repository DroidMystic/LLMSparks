package com.example.llmsparks.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.llmsparks.di.appModule
import com.example.llmsparks.presentation.ui.theme.LLMSparksTheme
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        startKoin { androidContext(this@MainActivity);modules(appModule) }
        setContent {
            LLMSparksTheme {
                LlmSparksApp()
            }
        }
    }
}

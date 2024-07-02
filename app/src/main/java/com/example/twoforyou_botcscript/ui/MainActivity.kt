package com.example.twoforyou_botcscript.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.twoforyou_botcscript.navigation.Navigation
import com.example.twoforyou_botcscript.ui.theme.Twoforyou_BOTCScriptTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Twoforyou_BOTCScriptTheme {
                val navController = rememberNavController()
                Navigation(navController)
            }
        }
    }
}
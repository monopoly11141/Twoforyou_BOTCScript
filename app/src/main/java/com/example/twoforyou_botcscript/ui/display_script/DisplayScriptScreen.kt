package com.example.twoforyou_botcscript.ui.display_script

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.twoforyou_botcscript.navigation.Screen

@Composable
fun DisplayScriptScreen(
    navController: NavController,
    viewModel: DisplayScriptViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column {
        Button(onClick = { navController.navigate(Screen.CharacterListScreen) }) {
            Text("모든 캐릭터 보기")
        }
    }
}
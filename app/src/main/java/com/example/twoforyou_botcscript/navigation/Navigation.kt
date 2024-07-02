package com.example.twoforyou_botcscript.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.twoforyou_botcscript.ui.character_list.CharacterListScreen
import com.example.twoforyou_botcscript.ui.display_script.DisplayScriptScreen
import com.example.twoforyou_botcscript.ui.script_detail.ScriptDetailScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.DisplayScriptScreen
    ) {
        composable<Screen.CharacterListScreen> {
            CharacterListScreen(
                navController = navController
            )
        }

        composable<Screen.DisplayScriptScreen> {
            DisplayScriptScreen(
                navController = navController
            )
        }

        composable<Screen.ScriptDetailScreen> {
            val args = it.toRoute<Screen.ScriptDetailScreen>()
            ScriptDetailScreen(
                navController = navController,
                args.scriptId
            )
        }

    }
}
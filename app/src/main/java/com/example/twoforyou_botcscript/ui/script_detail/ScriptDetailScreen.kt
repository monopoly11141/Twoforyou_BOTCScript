package com.example.twoforyou_botcscript.ui.script_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.twoforyou_botcscript.ui.composable.CharacterItem

@Composable
fun ScriptDetailScreen(
    navController: NavController,
    scriptId: Int,
    viewModel: ScriptDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.updateScriptByScriptId(scriptId)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(state.script.charactersObjectList) { character ->
                CharacterItem(character = character)
            }
        }
    }
}
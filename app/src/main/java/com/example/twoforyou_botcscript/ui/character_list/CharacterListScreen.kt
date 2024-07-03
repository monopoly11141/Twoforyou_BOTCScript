package com.example.twoforyou_botcscript.ui.character_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.twoforyou_botcscript.ui.character_list.composable.CheckboxMinimalExample

@Composable
fun CharacterListScreen(
    navController: NavController,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CheckboxMinimalExample(
            { viewModel.updateFilteredCharactersList() },
            { viewModel.updateFilteredCharactersListToAllCharacters() }
        )

        LazyColumn(
            modifier = Modifier,
        ) {
            items(state.filteredCharactersList) {
                Text(it.name)
            }
        }
    }

}
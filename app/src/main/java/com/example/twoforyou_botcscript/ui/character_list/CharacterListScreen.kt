package com.example.twoforyou_botcscript.ui.character_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.twoforyou_botcscript.data.model.helper.Script_List
import com.example.twoforyou_botcscript.ui.character_list.composable.ScriptFilterCheckList

@Composable
fun CharacterListScreen(
    navController: NavController,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val checked = remember { Script_List.entries.map { false }.toMutableStateList() }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            itemsIndexed(Script_List.entries) { i, scriptName ->
                ScriptFilterCheckList(
                    checked[i],
                    {
                        checked[i] = !checked[i]
                        if (checked[i]) {
                            viewModel.insertFilteredCharactersListByScript(scriptName)
                        } else {
                            viewModel.deleteFilterCharactersListByScript(scriptName)
                        }
                    },
                    scriptName.name
                )
            }
        }


        LazyColumn(
            modifier = Modifier,
        ) {
            items(state.filteredCharactersList) {
                Text(it.name)
            }
        }
    }

}
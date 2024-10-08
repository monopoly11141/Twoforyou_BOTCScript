package com.example.twoforyou_botcscript.ui.character_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.data.model.helper.Character_Type
import com.example.twoforyou_botcscript.data.model.helper.Script_List
import com.example.twoforyou_botcscript.ui.character_list.composable.ScriptFilterCheckList
import com.example.twoforyou_botcscript.ui.composable.CharacterItem

@Composable
fun CharacterListScreen(
    navController: NavController,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val checkedCharacterType = remember { Script_List.entries.map { false }.toMutableStateList() }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                itemsIndexed(Character_Type.entries) { i, characterType ->
                    ScriptFilterCheckList(
                        checkedCharacterType[i],
                        {
                            checkedCharacterType[i] = !checkedCharacterType[i]
                            if (checkedCharacterType[i]) {
                                viewModel.insertFilteredCharactersListByCharacterType(characterType)
                            } else {
                                viewModel.deleteFilteredCharactersListByCharacterType(characterType)
                            }
                        },
                        characterType.name
                    )
                }
            }

            LazyColumn(
                modifier = Modifier,
            ) {
                items(state.filteredCharactersList.toList()) { character ->

                    CharacterItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        character
                    )
                }
            }
        }
    }


}
package com.example.twoforyou_botcscript.ui.character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.data.model.helper.Character_Type
import com.example.twoforyou_botcscript.domain.repository.character_list.CharacterListRepository
import com.example.twoforyou_botcscript.ui.display_script.DisplayScriptUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: CharacterListRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterListUiState())
    val state = combine(
        repository.getAllCharacters(),
        _state
    ) { array ->
        CharacterListUiState(
            allCharactersList = array[0] as List<Character>
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)
}
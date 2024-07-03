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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: CharacterListRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterListUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    allCharactersList = repository.getAllCharacters().stateIn(viewModelScope).value,
                    filteredCharactersList = repository.getAllCharacters().stateIn(viewModelScope).value
                )
            }
        }
    }

    fun updateFilteredCharactersList() {
        _state.update {
            it.copy(
                filteredCharactersList = it.allCharactersList.filter {it.characterType == Character_Type.악마_DEMON}
            )
        }
    }

    fun updateFilteredCharactersListToAllCharacters() {
        _state.update {
            it.copy(
                filteredCharactersList = it.allCharactersList
            )
        }
    }


}
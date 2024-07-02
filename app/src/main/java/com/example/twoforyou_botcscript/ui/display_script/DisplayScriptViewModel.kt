package com.example.twoforyou_botcscript.ui.display_script

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.domain.repository.display_script.DisplayScriptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DisplayScriptViewModel @Inject constructor(
    private val repository: DisplayScriptRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DisplayScriptUiState())
    val state = combine(
        repository.getAllCharacters(),
        _state
    ) { array ->
        DisplayScriptUiState(
            allCharactersList = array[0] as List<Character>
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)
}
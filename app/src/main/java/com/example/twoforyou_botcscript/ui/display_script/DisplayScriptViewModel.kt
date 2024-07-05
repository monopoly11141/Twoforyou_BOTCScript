package com.example.twoforyou_botcscript.ui.display_script

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.data.model.Script
import com.example.twoforyou_botcscript.data.model.helper.Script_General_Info
import com.example.twoforyou_botcscript.domain.repository.display_script.DisplayScriptRepository
import com.example.twoforyou_botcscript.util.getEnglish
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayScriptViewModel @Inject constructor(
    private val repository: DisplayScriptRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DisplayScriptUiState())
    val state = combine(
        repository.getAllCharacters(),
        repository.getAllScript(),
        _state
    ) { array ->
        DisplayScriptUiState(
            allCharactersList = array[0] as List<Character>,
            scriptList = array[1] as List<Script>
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun insertScriptToDb(script: Script) {
        viewModelScope.launch {
            repository.insertScript(script)
        }
    }

    fun deleteScriptInDb(script: Script) {
        viewModelScope.launch {
            repository.deleteScript(script)
        }
    }

    fun deleteAllScriptInDb(script: Script) {
        viewModelScope.launch {
            repository.deleteAllScript()
        }
    }

    fun generateScriptFromJsonString(
        scriptName: String,
        scriptAuthor: String,
        jsonString: String
    ): Script {
        val script = Script(id = 0, Script_General_Info("", scriptAuthor, scriptName))

        var jsonCharactersStringList = jsonString.split("\"id\": \"[a-zA-Z\\s\\_\\']+\"")
        jsonCharactersStringList.forEach {
            it.replace("{\"id\": \"", "").replace("\"}", "").replace(" ", "").replace("\'", "")
        }
        Log.d("TAG", "DisplayScriptViewModel : goes in $jsonCharactersStringList")

        val charactersList = state.value.allCharactersList.filter { it.name.getEnglish() in jsonCharactersStringList }

        charactersList.map {
            script.charactersObjectList.add(it)
        }

        return script
    }


}
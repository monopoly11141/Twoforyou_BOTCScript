package com.example.twoforyou_botcscript.ui.display_script

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
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

    fun generateScript(
        fileName: String,
        jsonString: String,
        context: Context
    ): Script {

        val jsonScriptMetaRemoved = jsonString.replace("\"id\": \"_meta\"", "")

        val scriptNameRegex = Regex("(?<=\"name\":)\\s*\"[a-zA-z' \\d.-]*")
        var scriptName = scriptNameRegex.find(jsonScriptMetaRemoved)?.value?.trim()?.replace("\"", "") ?: ""
        if (scriptName.isBlank()) scriptName = fileName.replace(".json", "")

        val scriptAuthorRegex = Regex("(?<=\"author\":)\\s*\"[a-zA-z' \\d.-]*")
        val scriptAuthor = scriptAuthorRegex.find(jsonScriptMetaRemoved)?.value?.trim()?.replace("\"", "") ?: ""

        val script = Script(id = 0, Script_General_Info("", scriptAuthor, scriptName))

        var scriptCharacterRegex = Regex("(?<=\"id\": \")[a-zA-z' -]+")
        var jsonCharactersStringList =
            scriptCharacterRegex.findAll(jsonScriptMetaRemoved).map { it.value.replace("_", "").replace("-", "") }
                .toList()

        if (jsonCharactersStringList.isEmpty()) {
            scriptCharacterRegex = Regex("\"[a-zA-Z_\\s\\d' ]*\"")
            jsonCharactersStringList =
                scriptCharacterRegex.findAll(jsonScriptMetaRemoved).map {
                    it.value.trim().replace("_", "").replace("-", "").replace("\"", "")
                }.toList()

        }

        for (jsonCharacter in jsonCharactersStringList) {
            if (
                state.value.allCharactersList.filter {
                    it.name.getEnglish().lowercase() == jsonCharacter
                }.isEmpty()
            ) {
                continue
            }
            val character = state.value.allCharactersList.filter {
                it.name.getEnglish().lowercase() == jsonCharacter
            }[0]
            script.charactersObjectList.add(character)
        }
        return script
    }


    fun getFileNameFromUri(uri: Uri, contentResolver: ContentResolver): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor.use { it ->
                if (it != null && it.moveToFirst()) {
                    val cursorColumnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    result = it.getString(cursorColumnIndex)
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result!!.substring(cut + 1)
            }
        }
        return result!!
    }

}
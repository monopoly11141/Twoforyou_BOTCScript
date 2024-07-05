package com.example.twoforyou_botcscript.ui.script_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_botcscript.data.db.local.ScriptDao
import com.example.twoforyou_botcscript.domain.repository.script_detail.ScriptDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScriptDetailViewModel @Inject constructor(
    val scriptDao: ScriptDao,
    val repository: ScriptDetailRepository
): ViewModel() {

    private val _state = MutableStateFlow(ScriptDetailUiState())
    val state = _state.asStateFlow()

    fun updateScriptByScriptId(scriptId: Int) {
        viewModelScope.launch {
           _state.update {
               it.copy(
                   script = scriptDao.getScriptById(scriptId)
               )
           }
        }
    }

}
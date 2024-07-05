package com.example.twoforyou_botcscript.domain.repository.script_detail

import com.example.twoforyou_botcscript.data.model.Script

interface ScriptDetailRepository {
    suspend fun getScriptById(scriptId : Int) : Script
}
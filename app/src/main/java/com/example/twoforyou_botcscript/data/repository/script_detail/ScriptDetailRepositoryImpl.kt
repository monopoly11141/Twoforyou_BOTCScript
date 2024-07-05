package com.example.twoforyou_botcscript.data.repository.script_detail

import com.example.twoforyou_botcscript.data.db.local.ScriptDao
import com.example.twoforyou_botcscript.data.model.Script
import com.example.twoforyou_botcscript.domain.repository.script_detail.ScriptDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScriptDetailRepositoryImpl @Inject constructor(
    private val scriptDao: ScriptDao
) : ScriptDetailRepository {

    override suspend fun getScriptById(scriptId: Int): Script {
        return scriptDao.getScriptById(scriptId)
    }
}
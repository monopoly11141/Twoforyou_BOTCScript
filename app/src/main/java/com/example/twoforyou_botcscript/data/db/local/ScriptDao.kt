package com.example.twoforyou_botcscript.data.db.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.twoforyou_botcscript.data.model.Script
import kotlinx.coroutines.flow.Flow

@Dao
interface ScriptDao {

    @Query("SELECT * FROM script_database")
    fun getAllScript(): Flow<List<Script>>

    @Insert()
    suspend fun insertScript(script: Script)

    @Update
    suspend fun updateScript(script: Script)

    @Delete()
    suspend fun deleteScript(script: Script)

    @Query("DELETE FROM script_database")
    suspend fun deleteAllScript()

    @Query("SELECT * FROM script_database WHERE id = :id LIMIT 1")
    suspend fun getScriptById(id: Int): Script
}
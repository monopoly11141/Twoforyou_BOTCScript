package com.example.twoforyou_botcscript.data.model.helper

import androidx.room.ColumnInfo

data class Script_General_Info(
    @ColumnInfo(name = "script_general_info_id")
    val scriptGeneralInfoId: String = "",
    var author: String = "",
    var name: String = ""
)
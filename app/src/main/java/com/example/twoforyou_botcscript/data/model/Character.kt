package com.example.twoforyou_botcscript.data.model

import com.example.twoforyou_botcscript.data.model.helper.Character_Type
import com.example.twoforyou_botcscript.data.model.helper.Script_List

data class Character(
    var name: String = "",
    var scriptList: Script_List = Script_List.TroubleBrewing,
    var characterType: Character_Type = Character_Type.마을주민_TOWNSFOLK,
    var ability: String = "",
    var isFormatChangingRole: Boolean = false,
    var imageUrl: String = ""
)

fun Character.getEnglishName(): String {
    return this.name.filter {
        "^[A-Za-z]*$".toRegex().containsMatchIn(it.toString())
    }.replace("_", " ").trim()
}

fun Character.getKoreanName(): String {
    return this.name.filter {
        "^[ㄱ-ㅎ가-힣]*$".toRegex().containsMatchIn(it.toString())
    }.replace("_", " ").trim()
}

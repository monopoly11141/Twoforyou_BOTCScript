package com.example.twoforyou_botcscript.ui.character_list.composable

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.twoforyou_botcscript.util.getKorean

@Composable
fun ScriptFilterCheckList(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    text: String,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = text.getKorean()
        )
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChange()
            }
        )
    }

}
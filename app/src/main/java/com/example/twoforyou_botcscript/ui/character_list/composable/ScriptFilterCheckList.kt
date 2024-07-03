package com.example.twoforyou_botcscript.ui.character_list.composable

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun ScriptFilterCheckList(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    text: String,
) {

    Log.d("TAG", "Checked value : ${checked}")

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text
        )
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChange()
            }
        )
    }

}
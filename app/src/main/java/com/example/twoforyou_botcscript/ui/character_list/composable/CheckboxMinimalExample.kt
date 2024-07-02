package com.example.twoforyou_botcscript.ui.character_list.composable

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@Composable
fun CheckboxMinimalExample(
    isChecked : () -> Unit
) {
    var checked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            "Minimal checkbox"
        )
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
            }
        )
    }

    if(checked) {
        isChecked()
        Log.d("TAG", "Checkbox : goes in")
    }
}
package com.example.twoforyou_botcscript.ui.display_script.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twoforyou_botcscript.data.model.Script
import com.example.twoforyou_botcscript.ui.display_script.DisplayScriptViewModel

@Composable
fun InsertScriptDialog(
    jsonString : String,
    onInsertClicked: (Script) -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DisplayScriptViewModel = hiltViewModel()
) {
    var scriptTitleText by remember { mutableStateOf("") }
    var scriptAuthorText by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onCancelClicked() }) {
        Column {
            TextField(
                value = scriptTitleText,
                onValueChange = { updatedText ->
                    scriptTitleText = updatedText
                },
                label = {
                    Text("스크립트 제목")
                }
            )

            TextField(
                value = scriptAuthorText,
                onValueChange = { updatedText ->
                    scriptAuthorText = updatedText
                },
                label = {
                    Text("스크립트 작가")
                }
            )

            Button(
                onClick = {
                    val script = viewModel.generateScript(
                        scriptTitleText,
                        scriptAuthorText
                    )
                    onInsertClicked(script)

                    onCancelClicked()
                },
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text("Add Script")
            }
        }

    }
}
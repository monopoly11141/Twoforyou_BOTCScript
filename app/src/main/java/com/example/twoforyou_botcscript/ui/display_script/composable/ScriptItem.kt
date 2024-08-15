package com.example.twoforyou_botcscript.ui.display_script.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.twoforyou_botcscript.data.model.Script

@Composable
fun ScriptItem(
    script: Script,
    onClickDeleteButton: () -> Unit,
    onClickEditButton: () -> Unit,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var editIconButtonClicked by remember { mutableStateOf(false) }
    var deleteIconButtonClicked by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClickItem()
            }
            .padding(vertical = 2.dp, horizontal = 4.dp),
        shape = RoundedCornerShape(
            topStart = 20.dp,
            bottomEnd = 20.dp
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = script.scriptGeneralInfo.name,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(start = 12.dp, top = 8.dp, end = 8.dp),
                    fontSize = 16.sp
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(),
                    text = if (script.scriptGeneralInfo.author.isBlank()) "" else "by ${script.scriptGeneralInfo.author}",
                    fontSize = 12.sp
                )
            }

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .clickable { editIconButtonClicked = true }
                    .padding(horizontal = 4.dp, vertical = 12.dp)
            )

            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Delete",
                modifier = Modifier
                    .clickable { deleteIconButtonClicked = true }
                    .padding(horizontal = 4.dp, vertical = 12.dp)
            )

        }

    }

    /**
     * editButtonClicked
     */
    if (editIconButtonClicked) {
        var scriptNameTextField by remember { mutableStateOf(script.scriptGeneralInfo.name) }
        var scriptAuthorTextField by remember { mutableStateOf(script.scriptGeneralInfo.author) }

        Dialog(
            onDismissRequest = { editIconButtonClicked = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(
                        value = scriptNameTextField,
                        onValueChange = { text ->
                            scriptNameTextField = text
                        },
                        label = {
                            Text("시나리오 제목")
                        }
                    )

                    TextField(
                        value = scriptAuthorTextField,
                        onValueChange = { text ->
                            scriptAuthorTextField = text
                        },
                        label = {
                            Text("시나리오 저자")
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                    ) {
                        Button(
                            onClick = { editIconButtonClicked = false },
                            modifier = Modifier
                                .weight(0.5f)
                        ) {
                            Text(text = "취소")
                        }
                        Button(
                            onClick = {
                                script.scriptGeneralInfo.name = scriptNameTextField
                                script.scriptGeneralInfo.author = scriptAuthorTextField

                                onClickEditButton()

                                editIconButtonClicked = false
                            },
                            modifier = Modifier
                                .weight(0.5f)
                        ) {
                            Text(text = "수정")
                        }
                    }
                }
            }

        }
    }

    /**
     * deleteButtonClicked
     */
    if (deleteIconButtonClicked) {
        Dialog(
            onDismissRequest = { deleteIconButtonClicked = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${script.scriptGeneralInfo.name} 를 삭제합니까?",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                    ) {
                        Button(
                            onClick = { deleteIconButtonClicked = false },
                            modifier = Modifier
                                .weight(0.5f)
                        ) {
                            Text(text = "아니요.")
                        }
                        Button(
                            onClick = {
                                onClickDeleteButton()
                                deleteIconButtonClicked = false
                            },
                            modifier = Modifier
                                .weight(0.5f)
                        ) {
                            Text(text = "삭제합니다.")
                        }
                    }
                }
            }

        }
    }
}
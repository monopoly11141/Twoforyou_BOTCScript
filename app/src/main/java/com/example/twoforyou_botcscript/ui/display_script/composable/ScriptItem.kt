package com.example.twoforyou_botcscript.ui.display_script.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.twoforyou_botcscript.data.model.Script

@Composable
fun ScriptItem(
    script: Script,
    onClickDeleteButton: () -> Unit,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var deleteIconButtonClicked by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClickItem()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

//            Text(
//                text = "${script.scriptGeneralInfo!!.name} by ${script.scriptGeneralInfo.author}",
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier
//                    .padding(4.dp)
//            )

            Text(
                text = "${script.charactersObjectList}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { deleteIconButtonClicked = true },

                ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .padding(4.dp)
                )
            }

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
                            .align(Alignment.CenterVertically)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "${script.scriptGeneralInfo?.name}을 삭제합니까?")

                            Row(

                            ) {
                                Button(onClick = { deleteIconButtonClicked = false }) {
                                    Text(text = "아니요.")
                                }
                                Button(onClick = {
                                    onClickDeleteButton()
                                    deleteIconButtonClicked = false
                                }) {
                                    Text(text = "삭제합니다.")
                                }
                            }
                        }
                    }

                }
            }

        }
    }
}
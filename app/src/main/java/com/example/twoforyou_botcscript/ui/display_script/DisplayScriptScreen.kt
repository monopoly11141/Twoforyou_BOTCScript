package com.example.twoforyou_botcscript.ui.display_script

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.twoforyou_botcscript.navigation.Screen
import com.example.twoforyou_botcscript.ui.display_script.composable.InsertScriptDialog
import com.example.twoforyou_botcscript.ui.display_script.composable.ScriptItem
import java.io.File

@Composable
fun DisplayScriptScreen(
    navController: NavController,
    viewModel: DisplayScriptViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showInsertScriptDialog by remember { mutableStateOf(false) }

    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        result.value = it
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                items(state.scriptList) { script ->
                    ScriptItem(
                        script,
                        { viewModel.deleteScriptInDb(script) },
                        { navController.navigate(Screen.ScriptDetailScreen(script.id)) }
                    )

                    Divider(
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = {
                        showInsertScriptDialog = true
                    },
                    modifier = Modifier
                        .weight(0.5f)
                ) {
                    Text(
                        text = "json파일 추가",
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }

                Button(
                    onClick = {
                        navController.navigate(Screen.CharacterListScreen)
                    },
                    modifier = Modifier
                        .weight(0.5f)
                ) {
                    Text(
                        text = "전체 캐릭터 보기",
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }

                Button(onClick = {
                    launcher.launch(arrayOf("application/json"))
                }) {
                    Text(text = "Select Document")
                }
                result.value?.let { file ->
                    val a = readFileAsTextUsingInputStream(File(file.path).name)
                    Log.d("TAG", a)
                    Text(text = "Document Path: "+file.path.toString())
                }

            }

        }

    }

    if (showInsertScriptDialog) {
        InsertScriptDialog(
            onInsertClicked = { script -> viewModel.insertScriptToDb(script) },
            onCancelClicked = { showInsertScriptDialog = false }
        )
    }



}


fun readFileAsTextUsingInputStream(fileName: String)
        = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)
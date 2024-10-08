package com.example.twoforyou_botcscript.ui.display_script

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.twoforyou_botcscript.navigation.Screen
import com.example.twoforyou_botcscript.ui.display_script.composable.ScriptItem


@Composable
fun DisplayScriptScreen(
    navController: NavController,
    viewModel: DisplayScriptViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val contentResolver = LocalContext.current.contentResolver

    var fileUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        fileUri = it
    }
    var jsonString by remember { mutableStateOf("") }

    var addScriptButtonClicked by remember { mutableStateOf(false) }

    var searchText by remember { mutableStateOf("") }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {

            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                trailingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        "search"
                    )
                },
                label = {
                    Text("시나리오 제목이나 작가를 검색해보세요.")
                },
                modifier = Modifier
                    .fillMaxWidth(),
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                items(state.scriptList.filter {
                    it.scriptGeneralInfo.name.contains(searchText, true) or
                            it.scriptGeneralInfo.author.contains(searchText, true)
                }.sortedBy { it.scriptGeneralInfo.name }) { script ->
                    ScriptItem(
                        script,
                        { viewModel.deleteScriptInDb(script) },
                        { viewModel.updateScriptInDb(script) },
                        { navController.navigate(Screen.ScriptDetailScreen(script.id)) }
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screen.CharacterListScreen)
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 4.dp, end = 2.dp)
                ) {
                    Text(
                        text = "전체 캐릭터 보기",
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }

                Button(
                    onClick = {
                        launcher.launch(arrayOf("application/json"))
                        addScriptButtonClicked = true
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 2.dp, end = 4.dp)
                ) {
                    Text(text = "파일 선택하기 (.json)")
                }

                if (addScriptButtonClicked && fileUri != null) {
                    val fileName = viewModel.getFileNameFromUri(fileUri!!, contentResolver)

                    val inputStream = contentResolver.openInputStream(fileUri!!)!!
                    jsonString = inputStream.bufferedReader().use { it.readText() }

                    viewModel.generateScript(fileName, jsonString, LocalContext.current)?.let {
                        viewModel.insertScriptToDb(it)
                    }

                    addScriptButtonClicked = false
                }
            }

        }

    }

}



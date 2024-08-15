package com.example.twoforyou_botcscript.ui.script_detail

import android.Manifest
import androidx.collection.mutableLongListOf
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.ui.composable.CharacterItem

@Composable
fun ScriptDetailScreen(
    navController: NavController,
    scriptId: Int,
    viewModel: ScriptDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    viewModel.updateScriptByScriptId(scriptId)

    var characterList = mutableListOf(
        mutableListOf<Character>(),
        mutableListOf<Character>(),
        mutableListOf<Character>(),
        mutableListOf<Character>(),
        mutableListOf<Character>(),
    )

    state.script.charactersObjectList.forEach { character ->
        characterList[character.characterType.ordinal] += character
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            val pagerState = rememberPagerState(pageCount = {
                characterList.size
            })

            HorizontalPager(
                state = pagerState,
            ) { page ->
                // Our page content
               LazyColumn(
                   modifier = Modifier
                       .fillMaxHeight(0.95f)
               ) {
                   items(characterList[page]) { character ->
                    CharacterItem(character = character)
                }
               }
            }

            Button(
                onClick = {
                    isLoading = true
                    viewModel.makePdfFromScript(
                        state.script,
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ),
                        context
                    )
                    isLoading = false

                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("PDF 파일 만들기")
            }
        }

    }
}
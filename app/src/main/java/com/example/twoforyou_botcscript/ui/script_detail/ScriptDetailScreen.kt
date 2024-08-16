package com.example.twoforyou_botcscript.ui.script_detail

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.data.model.helper.Character_Type
import com.example.twoforyou_botcscript.ui.composable.CharacterItem
import com.example.twoforyou_botcscript.ui.theme.Demon_Color
import com.example.twoforyou_botcscript.ui.theme.Fabled_Color
import com.example.twoforyou_botcscript.ui.theme.Minion_Color
import com.example.twoforyou_botcscript.ui.theme.Outsider_Color
import com.example.twoforyou_botcscript.ui.theme.Townsfolk_Color
import com.example.twoforyou_botcscript.util.getEnglish
import com.example.twoforyou_botcscript.util.getKorean

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
            Text(
                text = "${Character_Type.entries[pagerState.currentPage].name.getKorean()} ${Character_Type.entries[pagerState.currentPage].name.getEnglish()}" ,
                color = when (Character_Type.entries[pagerState.currentPage]) {
                    Character_Type.마을주민_TOWNSFOLK -> Townsfolk_Color
                    Character_Type.외부인_OUTSIDER -> Outsider_Color
                    Character_Type.하수인_MINION -> Minion_Color
                    Character_Type.악마_DEMON -> Demon_Color
                    Character_Type.우화_FABLED -> Fabled_Color
                },
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(4.dp)
            )

            HorizontalPager(
                state = pagerState,
            ) { page ->
                // Our page content
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                ) {
                    items(characterList[page]) { character ->
                        CharacterItem(character = character)
                    }
                }
            }

            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(12.dp)
                    )
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
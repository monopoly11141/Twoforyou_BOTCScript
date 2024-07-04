package com.example.twoforyou_botcscript.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.example.twoforyou_botcscript.data.model.Character

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    character: Character
    ) {

    Box( modifier = modifier
        .fillMaxWidth()) {
        Row(
          modifier = Modifier
              .fillMaxWidth()
        ) {
            AsyncImage(model = character.imageUrl, contentDescription = "캐릭터 이미지")
        }
    }
}
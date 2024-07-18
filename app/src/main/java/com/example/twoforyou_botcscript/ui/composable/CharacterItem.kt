package com.example.twoforyou_botcscript.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.twoforyou_botcscript.data.model.Character
import com.example.twoforyou_botcscript.data.model.helper.Character_Type
import com.example.twoforyou_botcscript.ui.theme.Demon_Color
import com.example.twoforyou_botcscript.ui.theme.Fabled_Color
import com.example.twoforyou_botcscript.ui.theme.Minion_Color
import com.example.twoforyou_botcscript.ui.theme.Outsider_Color
import com.example.twoforyou_botcscript.ui.theme.Townsfolk_Color

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    character: Character
    ) {

    Box( modifier = modifier
        .fillMaxWidth()
        .border(
            1.dp,
            color = Color.Gray,
        )
        .padding(2.dp)
    ) {
        Row(
          modifier = Modifier
              .fillMaxWidth()
        ) {
            AsyncImage(
                model = character.imageUrl,
                contentDescription = "캐릭터 이미지",
                modifier = Modifier
                    .size(60.dp)
            )

            Text(
                text = character.ability.replace("\\n", "\n"),
                modifier = Modifier
                    .fillMaxWidth(),
                color = when(character.characterType) {
                    Character_Type.마을주민_TOWNSFOLK -> Townsfolk_Color
                    Character_Type.외부인_OUTSIDER -> Outsider_Color
                    Character_Type.하수인_MINION -> Minion_Color
                    Character_Type.악마_DEMON -> Demon_Color
                    Character_Type.우화_FABLED -> Fabled_Color
                }
            )

        }
    }
}
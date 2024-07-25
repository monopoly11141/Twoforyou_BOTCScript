package com.example.twoforyou_botcscript.ui.script_detail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.os.StrictMode
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_botcscript.data.db.local.ScriptDao
import com.example.twoforyou_botcscript.data.model.Script
import com.example.twoforyou_botcscript.data.model.helper.Character_Type
import com.example.twoforyou_botcscript.domain.repository.script_detail.ScriptDetailRepository
import com.example.twoforyou_botcscript.util.PermissionUtil
import com.example.twoforyou_botcscript.util.getEnglish
import com.example.twoforyou_botcscript.util.getKorean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class ScriptDetailViewModel @Inject constructor(
    val scriptDao: ScriptDao,
    val repository: ScriptDetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ScriptDetailUiState())
    val state = _state.asStateFlow()

    fun updateScriptByScriptId(scriptId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    script = scriptDao.getScriptById(scriptId)
                )
            }
        }
    }

    fun makePdfFromScript(
        script: Script,
        permissionList: Array<String>,
        context: Context
    )  {

        val pageWidth = 595
        val pageHeight = 842

        val pdfDocument = PdfDocument()
        val pdfDocumentPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val pdfDocumentPage = pdfDocument.startPage(pdfDocumentPageInfo)

        val canvas = pdfDocumentPage.canvas

        var characterType: Character_Type? = null

        /**
         * title
         */
        var titleYPosition = 20f
        val titlePaint = Paint()
        val titlePaintTextSize = 16f
        titlePaint.textSize = titlePaintTextSize
        titlePaint.setColor(Color.Black.toArgb())
        titlePaint.textAlign = Paint.Align.CENTER
        val titleText = "${script.scriptGeneralInfo.name} by ${script.scriptGeneralInfo.author}"
        canvas.drawText(
            titleText,
            pageWidth / 2f,
            titleYPosition,
            titlePaint
        )
        var characterYPosition = titleYPosition + titlePaintTextSize

        /**
         * character text paint
         */
        val characterTextPaint = Paint()
        val characterTextSize = 12f
        val characterNameXValue = 20f
        characterTextPaint.textSize = characterTextSize

        /**
         * character ability paint
         */
        val characterAbilityTextPaint = Paint()
        val characterAbilityTextSize = 8f
        val characterAbilityXValue = 160f
        characterAbilityTextPaint.textSize = characterAbilityTextSize
        characterTextPaint.textSize = characterTextSize

        /**
         * character image Paint
         */
        val characterImagePaint = Paint()
        val characterImageXValue = 110f
        val characterImageSize = 40

        /**
         * characterType box Paint
         */
        val boxPaint = Paint()
        boxPaint.setColor(Color.Black.toArgb())
        boxPaint.strokeWidth = 0f
        boxPaint.style = Paint.Style.STROKE

        /**
         * fabled Character
         */
        var fabledCharacterXValue = 10f
        val fabledCharacterImageSize = 40

        val incrementY = (pageHeight - titleYPosition) / (script.charactersObjectList.size + 1)
        val characterTypeXValue = 535f

        for (character in state.value.script.charactersObjectList) {

            if (character.characterType == Character_Type.우화_FABLED) {
                val bitmap = getBitmapFromURL(character.imageUrl)

                bitmap?.let {
                    val bitmapSized =
                        Bitmap.createScaledBitmap(
                            bitmap,
                            fabledCharacterImageSize,
                            fabledCharacterImageSize,
                            true
                        )
                    canvas.drawBitmap(
                        bitmapSized,
                        fabledCharacterXValue,
                        -10f,
                        characterImagePaint
                    )

                    fabledCharacterXValue += fabledCharacterImageSize
                }
                continue
            }

            if (character.characterType == Character_Type.마을주민_TOWNSFOLK ||
                character.characterType == Character_Type.외부인_OUTSIDER
            ) {
                characterTextPaint.color = Color.Blue.toArgb()
                characterAbilityTextPaint.color = Color.Blue.toArgb()
                boxPaint.color = Color.Blue.toArgb()
            } else {
                characterTextPaint.color = Color.Red.toArgb()
                characterAbilityTextPaint.color = Color.Red.toArgb()
                boxPaint.color = Color.Red.toArgb()
            }

            if (characterType != character.characterType) {
                characterType = character.characterType
                /**
                 * draw characterType label
                 */
                canvas.drawText(
                    characterType.name.getKorean(),
                    characterTypeXValue,
                    characterYPosition - characterTextSize + (characterTextSize / 2),
                    characterTextPaint
                )

                /**
                 * draw characterType Rectangle
                 */
                val characterTypeRectangleEndX =
                    characterTypeXValue + characterType.name.getKorean().length * 12
                canvas.drawRect(
                    characterTypeXValue,
                    characterYPosition - characterTextSize * 2,
                    characterTypeRectangleEndX,
                    characterYPosition - characterTextSize + (characterTextSize),
                    boxPaint
                )

                /**
                 * draw line to pdf
                 */
                canvas.drawLine(
                    0f,
                    characterYPosition - characterTextSize,
                    characterTypeXValue,
                    characterYPosition - characterTextSize + 1,
                    characterTextPaint
                )
                canvas.drawLine(
                    characterTypeRectangleEndX,
                    characterYPosition - characterTextSize,
                    pageWidth.toFloat(),
                    characterYPosition - characterTextSize + 1,
                    characterTextPaint
                )

            } else {
                /**
                 * draw line to pdf
                 */
                canvas.drawLine(
                    0f,
                    characterYPosition - characterTextSize,
                    pageWidth.toFloat(),
                    characterYPosition - characterTextSize + 1,
                    characterTextPaint
                )
            }


            /**
             * adding korean name to pdf
             */
            canvas.drawText(
                character.name.getKorean(),
                characterNameXValue,
                characterYPosition,
                characterTextPaint
            )

            /**
             * adding english name to pdf
             */
            canvas.drawText(
                character.name.getEnglish(),
                characterNameXValue,
                characterYPosition + characterTextSize.toInt(),
                characterTextPaint
            )

            /**
             * adding character ability to pdf
             */
            val characterAbilityTextSplitArray = character.ability.split("\\n")
            var characterAbilityYPosition =
                characterYPosition - characterTextSize + characterAbilityTextSize + 1
            for (characterAbilitySplitString in characterAbilityTextSplitArray) {
                canvas.drawText(
                    characterAbilitySplitString,
                    characterAbilityXValue,
                    characterAbilityYPosition,
                    characterAbilityTextPaint
                )
                characterAbilityYPosition += characterAbilityTextSize + 1
            }

            /**
             * adding image to pdf
             */
            val bitmap = getBitmapFromURL(character.imageUrl)

            bitmap?.let {
                val bitmapSized =
                    Bitmap.createScaledBitmap(bitmap, characterImageSize, characterImageSize, true)
                canvas.drawBitmap(
                    bitmapSized,
                    characterImageXValue,
                    characterYPosition - (characterImageSize / 2),
                    characterImagePaint
                )
            }

            /**
             * incrementing character Y Position
             */
            characterYPosition += incrementY

        }

        /**
         * 첫 밤 제외
         */
        val exceptFirstNightPaint = Paint()
        val exceptFirstNighTextSize = 10f
        val exceptFirstNightYValue = 830f
        exceptFirstNightPaint.textSize = exceptFirstNighTextSize
        canvas.drawText(
            "* = 첫 밤 제외",
            characterNameXValue,
            exceptFirstNightYValue,
            exceptFirstNightPaint
        )

        val childForPdfFile = "${
            script.scriptGeneralInfo.name
                .replace(" ", "_")
                .replace(":", ".")
                .replace("\t", "")
                .replace("\n", "")
        }.pdf"


        val pdfFile =
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath,
                childForPdfFile
            )
        pdfDocument.finishPage(pdfDocumentPage)

        PermissionUtil().requestPermission(permissionList, context)

        try {
            pdfDocument.writeTo(FileOutputStream(pdfFile))

            Toast.makeText(context, "pdf파일 만들기 성공", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()

            Toast.makeText(context, "pdf파일 만들기 실패", Toast.LENGTH_SHORT)
                .show()
        }
        pdfDocument.close()

    }


    private fun getBitmapFromURL(src: String): Bitmap? {

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val myBitmap = BitmapFactory.decodeStream(input)
            return myBitmap
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}
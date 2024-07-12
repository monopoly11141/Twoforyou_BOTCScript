package com.example.twoforyou_botcscript.ui.script_detail

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_botcscript.data.db.local.ScriptDao
import com.example.twoforyou_botcscript.data.model.Script
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
    ) {
        val pageWidth = 595
        val pageHeight = 842

        val pdfDocument = PdfDocument()
        val pdfDocumentPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val pdfDocumentPage = pdfDocument.startPage(pdfDocumentPageInfo)

        val canvas = pdfDocumentPage.canvas

        var currentYPosition = 20f
        /**
         * title
         */
        val titlePaint = Paint()
        val titlePaintTextSize = 16f
        titlePaint.textSize = titlePaintTextSize
        titlePaint.setColor(Color.Black.toArgb())
        titlePaint.textAlign = Paint.Align.CENTER
        val titleText = "${script.scriptGeneralInfo.name} by ${script.scriptGeneralInfo.author}"
        canvas.drawText(
            titleText,
            pageWidth / 2f,
            currentYPosition,
            titlePaint
        )
        currentYPosition += titlePaintTextSize
        /**
         * character
         */
        val characterTextPaint = Paint()
        val characterTextSize = 12f
        val characterNameXValue = 50f
        characterTextPaint.textSize = characterTextSize
        val incrementY = (pageHeight - currentYPosition) / (script.charactersObjectList.size + 1)
        for(character in state.value.script.charactersObjectList) {
            canvas.drawText(
                character.name.getKorean(),
                characterNameXValue,
                currentYPosition,
                characterTextPaint
            )

            canvas.drawText(
                character.name.getEnglish(),
                characterNameXValue,
                currentYPosition + characterTextSize.toInt(),
                characterTextPaint
            )

            currentYPosition += incrementY

        }


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



}
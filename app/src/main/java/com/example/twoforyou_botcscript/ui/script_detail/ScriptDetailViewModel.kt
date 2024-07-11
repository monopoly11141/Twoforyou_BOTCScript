package com.example.twoforyou_botcscript.ui.script_detail

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_botcscript.data.db.local.ScriptDao
import com.example.twoforyou_botcscript.data.model.Script
import com.example.twoforyou_botcscript.domain.repository.script_detail.ScriptDetailRepository
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
        context: Context
    ) {
        val pageWidth = 595
        val pageHeight = 842
        val startingYValue = 20f

        val pdfDocument = PdfDocument()
        val pdfDocumentPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val pdfDocumentPage = pdfDocument.startPage(pdfDocumentPageInfo)

        val canvas = pdfDocumentPage.canvas

        val titlePaint = Paint()
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        titlePaint.textSize = 18F
        titlePaint.setColor(Color.Black.toArgb())
        titlePaint.textAlign = Paint.Align.CENTER
        val titleText = "${script.scriptGeneralInfo.name} by ${script.scriptGeneralInfo.author}"

        canvas.drawText(
            titleText,
            pageWidth / 2f,
            startingYValue,
            titlePaint
        )

        val childForPdfFile = "${
            script.scriptGeneralInfo.name
                .replace(" ", "_")
                .replace(":", ".")
                .replace("\t", "")
                .replace("\n", "")
        }.pdf"

        Log.d(TAG, "generatePdf: $childForPdfFile")
        val pdfFile =
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath,
                childForPdfFile
            )
        pdfDocument.finishPage(pdfDocumentPage)
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

    fun requestPermission(requestPermissions: Array<String>, context: Context) {

        val PERMISSIONS_REQUEST_CODE = 100

        val activity = context.getActivityOrNull()!!
        val permissionCheck =
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    requestPermissions,
                    PERMISSIONS_REQUEST_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    activity,
                    requestPermissions,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }
    }

    fun Context.getActivityOrNull(): Activity? {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }

        return null
    }


}
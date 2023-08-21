package com.mk.develop.simplewebview.webutils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.core.content.FileProvider
import com.mk.develop.simplewebview.utils.AppConst.IMAGE_PACKAGE_NAME
import com.mk.develop.simplewebview.utils.AppConst.IMAGE_PATH
import com.mk.develop.simplewebview.utils.decrypt
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun showFileChooserPhoto(
    cameraIntent: Intent?,
): Intent {
    val galleryIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = IMAGE_PATH
        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
    }
    val chooserIntent = Intent(Intent.ACTION_CHOOSER).run {
        putExtra(Intent.EXTRA_INTENT, galleryIntent)
        putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            cameraIntent?.let { arrayOf(it) } ?: arrayOfNulls(0))
    }

    return chooserIntent
}

internal fun getCameraIntent(
    context: Context,
    imageUri: MutableState<Uri?>,
): Intent? {
    val time: String = SimpleDateFormat("eXl5eU1NZGRfSEhtbXNz".decrypt(), Locale.getDefault()).format(Date())
    val storage = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val file = File.createTempFile("${"UEhPVE9fSlBFR18=".decrypt()}${time}_", "LmpwZw==".decrypt(), storage)
    val authority = context.packageName + IMAGE_PACKAGE_NAME
    val uri = FileProvider.getUriForFile(
        context,
        authority,
        file,
    )
    imageUri.value = uri
    return try {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, uri)
        }
    } catch (_: Exception) {
        null
    }
}
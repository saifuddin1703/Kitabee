package com.example.kitabee.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream

@RequiresApi(Build.VERSION_CODES.Q)
fun getFileFromUri(uri: Uri, context:Context): File? {

    var file:File? = null

    val cursor = context.contentResolver.query(
        uri, arrayOf(MediaStore.Images.ImageColumns.DISPLAY_NAME),null,null,null
    )

    val data_index = MediaStore.Images.ImageColumns.DISPLAY_NAME

    val index = cursor?.getColumnIndexOrThrow(data_index)
    cursor?.moveToFirst()

    if (index != null) {
        val name = cursor.getString(index)
        cursor.close()
        file = File(context.externalCacheDir,name)
        file.createNewFile()
        val outputStream = FileOutputStream(file)
        val inputStream = context.contentResolver.openInputStream(uri)
        if (inputStream != null) {
            FileUtils.copy(inputStream,outputStream)
            outputStream.flush()


            Log.d("tag",file.toURI().toString())
        }
    }
    else
        Log.d("tag","error")

    return file
}
package com.example.app.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

object ImageUtils {

    fun saveImageToGallery(context: Context, imageUrl: String) {
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    saveBitmapToFile(context, resource)
                }

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {
                    // no-op
                }
            })
    }

    private fun saveBitmapToFile(context: Context, bitmap: Bitmap) {
        val filename = "photo_${System.currentTimeMillis()}.jpg"
        val directory = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        } else {
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        }
        val file = File(directory, filename)

        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            addImageToGallery(context, file)
            Toast.makeText(context, "이미지가 저장되었습니다", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "이미지 저장에 실패했습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addImageToGallery(context: Context, file: File) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DATA, file.absolutePath)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            }
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        } else {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
            val uri =
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            uri?.let {
                context.contentResolver.openFileDescriptor(it, "w")?.use { pfd ->
                    FileOutputStream(pfd.fileDescriptor).use { out ->
                        FileInputStream(file).use { inputStream ->
                            inputStream.copyTo(out)
                        }
                    }
                }
                values.clear()
                values.put(MediaStore.Images.Media.IS_PENDING, 0)
                context.contentResolver.update(it, values, null, null)
            }
        }
    }
}

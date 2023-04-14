package com.looker.gradiente.utils

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun ImageBitmap.writeToBitmap(brush: Brush) = withContext(Dispatchers.IO) {
    val canvas = Canvas(this@writeToBitmap)
    val paint = Paint()
    brush.applyTo(Size(width.toFloat(), height.toFloat()), paint, 1f)
    canvas.drawRect(Size(width.toFloat(), height.toFloat()).toRect(), paint)
}

fun imageBitmap(size: Size) = Bitmap.createBitmap(
    size.width.toInt(),
    size.height.toInt(),
    Bitmap.Config.ARGB_8888
).asImageBitmap()
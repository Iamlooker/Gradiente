package com.looker.gradiente.utils

import android.app.WallpaperManager
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asAndroidBitmap

suspend fun Context.setWallpaperCompat(brush: Brush) {
    val manager = WallpaperManager.getInstance(this)
    val imageBitmap = imageBitmap(screenSize()).apply { writeToBitmap(brush) }
    manager.setBitmap(imageBitmap.asAndroidBitmap())
}

fun Context.screenSize(): Size {
    val resources: Resources = resources
    val displayMetrics: DisplayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels.toFloat()
    val screenHeight = displayMetrics.heightPixels.toFloat()
    return Size(screenWidth, screenHeight)
}
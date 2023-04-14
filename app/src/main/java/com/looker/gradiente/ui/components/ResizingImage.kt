package com.looker.gradiente.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.BrushPainter

@Composable
fun ResizingImage(
    brush: Brush?,
    modifier: Modifier = Modifier
) {
    if (brush != null) {
        Image(
            modifier = modifier,
            painter = BrushPainter(brush),
            contentDescription = null
        )
    }
}
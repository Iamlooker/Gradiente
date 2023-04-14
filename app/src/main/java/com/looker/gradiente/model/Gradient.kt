package com.looker.gradiente.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Entity("gradient")
@Stable
data class Gradient(
    val colors: ImmutableList<ArgbStops>,
    val direction: GradientDirection,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)

@Serializable
@Stable
sealed interface GradientDirection {

    @Serializable
    data class Angle(val start: Float, val end: Float) : GradientDirection

    @Serializable
    object DEFAULT : GradientDirection

}

typealias ArgbStops = Pair<Float, Int>
typealias ColorStops = Pair<Float, Color>

private fun List<ColorStops>.toArgbStops(): List<ArgbStops> = map {
    it.first to it.second.toArgb()
}

private fun List<ArgbStops>.toColorStops(): Array<ColorStops> = map {
    it.first to Color(it.second)
}.toTypedArray()

fun Gradient.toBrush(): Brush {
    val startOffset = when (direction) {
        is GradientDirection.Angle -> Offset(direction.start, 0f)
        GradientDirection.DEFAULT -> Offset.Zero
    }
    val endOffset = when (direction) {
        is GradientDirection.Angle -> Offset(direction.end, Float.POSITIVE_INFINITY)
        GradientDirection.DEFAULT -> Offset.Infinite
    }
    return Brush.linearGradient(
        colorStops = colors.toColorStops(),
        start = startOffset,
        end = endOffset
    )
}

fun Gradient.toHorizontalBrush(): Brush =
    Brush.horizontalGradient(colorStops = colors.toColorStops())

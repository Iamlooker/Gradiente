package com.looker.gradiente.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.looker.gradiente.model.Gradient
import com.looker.gradiente.model.toHorizontalBrush
import com.looker.gradiente.utils.toPx

@Composable
fun GradientItem(
    brush: Brush,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .requiredSize(70.dp)
            .gradientItemBackground { selected }
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onClick
            )
            .padding(12.dp)
            .background(brush, CircleShape)
            .border(
                width = (1.8).dp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = CircleShape
            )
    )
}

fun LazyGridScope.gradientGrid(
    gradients: List<Gradient>,
    selected: Gradient?,
    onClick: (Gradient) -> Unit
) = items(
    items = gradients,
    key = { it.id!! },
    contentType = { "" }
) { gradient ->
    GradientItem(
        brush = gradient.toHorizontalBrush(),
        selected = gradient == selected,
        onClick = { onClick(gradient) }
    )
}

@Stable
fun Modifier.gradientItemBackground(
    selectedState: () -> Boolean
): Modifier = composed {
    val state = updateTransition(targetState = selectedState(), label = "")
    val color by state.animateColor(label = "") {
        if (it) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.secondaryContainer
    }
    val shape by state.animateFloat(label = "") {
        if (it) 18.dp.toPx() else 36.dp.toPx()
    }

    drawBehind {
        drawRoundRect(color = color, cornerRadius = CornerRadius(shape, shape))
    }
}
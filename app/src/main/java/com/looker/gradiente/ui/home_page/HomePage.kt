package com.looker.gradiente.ui.home_page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.looker.gradiente.model.toBrush
import com.looker.gradiente.ui.components.gradientGrid

val SPECIAL_TEXT =
    "GRADIENTE ".repeat(200)

@OptIn(ExperimentalTextApi::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomePageViewModel = viewModel()
) {
    val context = LocalContext.current
    val homeState by viewModel.homeState.collectAsState()
    val style = MaterialTheme.typography.headlineSmall
    val color = MaterialTheme.colorScheme.outline.copy(0.05f)
    val textMeasurer = rememberTextMeasurer()
    val lazyState = rememberLazyGridState()
    val offset by remember {
        derivedStateOf {
            lazyState.firstVisibleItemScrollOffset.toFloat() * (0.5F)
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                withTransform({
                    translate(offset, offset)
                    rotate(25f)
                    scale(10f)
                }) {
                    drawText(
                        textMeasurer = textMeasurer,
                        text = SPECIAL_TEXT,
                        softWrap = true,
                        style = style.copy(color = color)
                    )
                }
            },
        horizontalAlignment = Alignment.End
    ) {
        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.safeContent))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 32.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .drawWithCache {
                    val gradient = homeState.selectedGradient ?: homeState.gradients.firstOrNull()
                    val canShow = gradient != null
                    val brush = gradient?.toBrush()
                    onDrawBehind {
                        if (canShow) drawRect(brush!!)
                    }
                }
        )
        Spacer(modifier = Modifier.height(24.dp))
        LazyHorizontalGrid(
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth(),
            rows = GridCells.Fixed(2),
            state = lazyState,
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            gradientGrid(
                homeState.gradients,
                homeState.selectedGradient,
                onClick = viewModel::selectGradient
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Spacer(modifier = Modifier.width(12.dp))
//            OutlinedButton(
//                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
//                onClick = { }
//            ) {
//                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(text = "Add New")
//            }
//            Spacer(modifier = Modifier.width(8.dp))
            AnimatedVisibility(visible = viewModel.newSelected) {
                FilledTonalButton(
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                    onClick = { viewModel.setWallpaper(context) }
                ) {
                    Text(text = "Apply")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.Rounded.Done, contentDescription = null)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.safeContent))
    }
}
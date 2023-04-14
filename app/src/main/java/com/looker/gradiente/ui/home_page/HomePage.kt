package com.looker.gradiente.ui.home_page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.looker.gradiente.model.toBrush
import com.looker.gradiente.ui.components.gradientGrid

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomePageViewModel = viewModel()
) {
    val context = LocalContext.current
    val homeState by viewModel.homeState.collectAsState()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(32.dp)
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
        LazyHorizontalGrid(
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth(),
            rows = GridCells.Fixed(2),
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
        Spacer(modifier = Modifier.height(24.dp))
    }
}
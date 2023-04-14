package com.looker.gradiente.ui.home_page

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.looker.gradiente.data.SettingsRepository
import com.looker.gradiente.data.db.dao.GradientDao
import com.looker.gradiente.model.Gradient
import com.looker.gradiente.model.toBrush
import com.looker.gradiente.utils.setWallpaperCompat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val dao: GradientDao,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val homeStateStream = combine(
        dao.getAllGradients(),
        settingsRepository.settingsFlow
    ) { gradients, settings ->
        val currentGradient = dao.getGradient(settings.selectedGradient)
        HomePageUiState(currentGradient, gradients.toImmutableList())
    }

    var newSelected by mutableStateOf(false)

    val homeState = homeStateStream.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomePageUiState()
    )

    fun selectGradient(gradient: Gradient) {
        viewModelScope.launch {
            if (settingsRepository.fetchInitialPreferences().selectedGradient != gradient.id) {
                newSelected = true
                settingsRepository.setGradientId(gradient.id!!)
            }
        }
    }

    fun setWallpaper(context: Context) {
        viewModelScope.launch {
            val gradientId = settingsRepository.fetchInitialPreferences().selectedGradient
            val selectedGradient = dao.getGradient(gradientId)
            context.setWallpaperCompat(selectedGradient.toBrush())
            newSelected = false
        }
    }
}

@Immutable
data class HomePageUiState(
    val selectedGradient: Gradient? = null,
    val gradients: ImmutableList<Gradient> = persistentListOf()
)
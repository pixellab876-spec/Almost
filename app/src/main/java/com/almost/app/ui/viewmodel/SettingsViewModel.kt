package com.almost.app.ui.viewmodel
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.almost.app.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SettingsUiState(val themeMode: ThemeMode = ThemeMode.SYSTEM, val notificationsEnabled: Boolean = true, val defaultLeadTime: Int = 3)
class SettingsViewModel(app: Application) : AndroidViewModel(app) {
    private val prefs = app.getSharedPreferences("almost_settings", Context.MODE_PRIVATE)
    private val _uiState = MutableStateFlow(SettingsUiState(ThemeMode.valueOf(prefs.getString("theme", "SYSTEM") ?: "SYSTEM"), prefs.getBoolean("notifications", true), prefs.getInt("lead", 3)))
    val uiState = _uiState.asStateFlow()
    fun setTheme(mode: ThemeMode) { prefs.edit().putString("theme", mode.name).apply(); _uiState.value = _uiState.value.copy(themeMode = mode) }
    fun setNotifications(enabled: Boolean) { prefs.edit().putBoolean("notifications", enabled).apply(); _uiState.value = _uiState.value.copy(notificationsEnabled = enabled) }
    fun setLeadTime(days: Int) { prefs.edit().putInt("lead", days).apply(); _uiState.value = _uiState.value.copy(defaultLeadTime = days) }
}

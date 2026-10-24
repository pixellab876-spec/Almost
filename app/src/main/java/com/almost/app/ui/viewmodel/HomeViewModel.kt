package com.almost.app.ui.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.almost.app.AlmostApplication
import com.almost.app.data.local.entity.InventoryItemEntity
import com.almost.app.data.repository.InventoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
class HomeViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = InventoryRepository((app as AlmostApplication).database.inventoryDao())
    val items = repo.allItems.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun delete(item: InventoryItemEntity) { viewModelScope.launch { repo.delete(item) } }
}

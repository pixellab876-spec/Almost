package com.almost.app.ui.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.almost.app.AlmostApplication
import com.almost.app.data.local.entity.InventoryItemEntity
import com.almost.app.data.repository.InventoryRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
class ItemDetailViewModel(app: Application, val itemId: Long) : AndroidViewModel(app) {
    private val repo = InventoryRepository((app as AlmostApplication).database.inventoryDao())
    val item = repo.item(itemId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val consumption = repo.consumptionHistory(itemId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val restocks = repo.restockHistory(itemId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun consume(amount: Double, done: (String?) -> Unit) { viewModelScope.launch { try { val current = item.value ?: error("Item not found"); repo.consume(current, amount); done(null) } catch (e: Exception) { done(e.message ?: "Could not record consumption") } } }
    fun restock(amount: Double, done: (String?) -> Unit) { viewModelScope.launch { try { val current = item.value ?: error("Item not found"); repo.restock(current, amount); done(null) } catch (e: Exception) { done(e.message ?: "Could not record restock") } } }
    fun delete(done: () -> Unit) { viewModelScope.launch { item.value?.let { repo.delete(it) }; done() } }
}

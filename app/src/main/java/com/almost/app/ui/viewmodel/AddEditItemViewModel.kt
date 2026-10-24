package com.almost.app.ui.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.almost.app.AlmostApplication
import com.almost.app.data.local.entity.InventoryItemEntity
import com.almost.app.data.repository.InventoryRepository
import kotlinx.coroutines.launch
class AddEditItemViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = InventoryRepository((app as AlmostApplication).database.inventoryDao())
    fun save(id: Long?, name: String, category: String, quantity: String, unit: String, usage: String, lead: String, threshold: String, done: (String?) -> Unit) {
        val q = quantity.toDoubleOrNull(); val u = usage.toDoubleOrNull() ?: 0.0; val l = lead.toIntOrNull() ?: 3; val t = threshold.toDoubleOrNull() ?: 0.0
        if (name.isBlank()) { done("Enter an item name."); return }
        if (q == null || q < 0 || u < 0 || l < 0 || t < 0) { done("Quantity, usage, lead time and threshold must be valid non-negative numbers."); return }
        viewModelScope.launch { try { val now = System.currentTimeMillis(); val old = id?.let { repo.get(it) }; repo.save(InventoryItemEntity(id = old?.id ?: 0, name = name.trim(), category = category.ifBlank { "General" }, currentQuantity = q, unit = unit.ifBlank { "pieces" }, dailyUsageEstimate = u, reminderLeadTime = l, minimumQuantityThreshold = t, createdDate = old?.createdDate ?: now, lastUpdatedDate = now, lastRestockedDate = old?.lastRestockedDate ?: now)); done(null) } catch (e: Exception) { done(e.message ?: "Could not save item") } }
    }
}

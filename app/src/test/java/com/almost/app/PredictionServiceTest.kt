package com.almost.app

import com.almost.app.data.local.entity.InventoryItemEntity
import com.almost.app.domain.prediction.PredictionService
import org.junit.Assert.*
import org.junit.Test

class PredictionServiceTest {
    private fun item(quantity: Double, usage: Double) = InventoryItemEntity(name = "Sugar", currentQuantity = quantity, unit = "kg", dailyUsageEstimate = usage, createdDate = 1L, lastUpdatedDate = 1L, lastRestockedDate = 1L)
    @Test fun zeroQuantityIsFinished() { val p = PredictionService.calculate(item(0.0, 1.0), 1000L); assertEquals("Finished", p.status); assertEquals(0.0, p.daysRemaining!!, 0.0); assertEquals(1000L, p.predictedFinishDate) }
    @Test fun predictsDaysRemaining() { val p = PredictionService.calculate(item(6.0, 2.0), 0L); assertEquals(3.0, p.daysRemaining!!, 0.001); assertEquals(259200000L, p.predictedFinishDate) }
    @Test fun unknownUsageDoesNotInventDate() { val p = PredictionService.calculate(item(5.0, 0.0), 0L); assertNull(p.daysRemaining); assertNull(p.predictedFinishDate); assertEquals("Prediction unavailable", p.status) }
}

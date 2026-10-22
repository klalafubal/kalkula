package com.example.kal

object HistoryManager {
    private val historyList = mutableListOf<String>()

    // Добавить вычисление в историю (максимум 5 штук)
    fun addCalculation(item: String) {
        historyList.add(0, item) // добавляем в самый верх
        if (historyList.size > 5) {
            historyList.removeAt(historyList.size - 1) // удаляем старые, если больше 5
        }
    }

    // Получить список истории
    fun getHistory(): List<String> {
        return historyList
    }
}
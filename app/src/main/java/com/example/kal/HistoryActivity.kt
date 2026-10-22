package com.example.kal

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "HistoryActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        Log.d(TAG, "onCreate: экран истории создан")

        val container = findViewById<LinearLayout>(R.id.layoutHistoryContainer)
        val historyList = HistoryManager.getHistory()

        // Очищаем контейнер на всякий случай
        container.removeAllViews()

        if (historyList.isEmpty()) {
            val tv = TextView(this)
            tv.text = "История пока пуста"
            tv.setTextColor(resources.getColor(android.R.color.white, null))

            tv.textSize = 18f
            container.addView(tv)
        } else {
            // Выводим сохраненные элементы
            for (item in historyList) {
                val tv = TextView(this)
                tv.text = item
                tv.setTextColor(resources.getColor(android.R.color.white, null))
                tv.textSize = 22f
                tv.setPadding(0, 12, 0, 12)
                container.addView(tv)
            }
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            Log.i(TAG, "Нажата кнопка возврата назад")
            finish()
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }
}
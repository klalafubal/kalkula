package com.example.kal

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

class AboutActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SettingsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        Log.d(TAG, "onCreate: экран настроек создан")

        val switchSound = findViewById<SwitchCompat>(R.id.switchSound)
        val sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)

        // Читаем сохраненное состояние (по умолчанию звук включен — true)
        val isSoundEnabled = sharedPreferences.getBoolean("SOUND_ENABLED", true)
        switchSound.isChecked = isSoundEnabled

        // Сохранение выбора пользователя
        switchSound.setOnCheckedChangeListener { _, isChecked ->
            Log.i(TAG, "Пользователь переключил звук: enabled = $isChecked")
            sharedPreferences.edit().putBoolean("SOUND_ENABLED", isChecked).apply()
        }

        val switchTheme = findViewById<SwitchCompat>(R.id.switchTheme)

        // Читаем сохраненное состояние (по умолчанию темная тема включена — true)
        val isDarkMode = sharedPreferences.getBoolean("DARK_MODE", true)
        switchTheme.isChecked = isDarkMode

        // Обработка переключения темы
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            Log.i(TAG, "Пользователь переключил тему: темная = $isChecked")

            // Сохраняем выбор в память
            sharedPreferences.edit().putBoolean("DARK_MODE", isChecked).apply()

            // Применяем тему во всем приложении
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        findViewById<Button>(R.id.btnBackSettings).setOnClickListener {
            Log.i(TAG, "Нажата кнопка возврата назад из настроек")
            finish()
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }
}
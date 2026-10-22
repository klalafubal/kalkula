package com.example.kal

import android.content.Intent
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import net.objecthunter.exp4j.ExpressionBuilder
import android.widget.Button
import android.util.Log

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Функция проверки и воспроизведения звука клика
        fun playClickSound() {
            val sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)
            val isSoundEnabled = sharedPreferences.getBoolean("SOUND_ENABLED", true)
            if (isSoundEnabled) {
                window.decorView.rootView.playSoundEffect(android.view.SoundEffectConstants.CLICK)
            }
        }



        val sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("DARK_MODE", true)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 1. Экраны вывода и прокрутка
        val result: TextView = findViewById(R.id.result)
        val operation: TextView = findViewById(R.id.operation)
        val scrollViewOperation: HorizontalScrollView = findViewById(R.id.scrollViewOperation)

        val bHistory: Button = findViewById(R.id.b_history)
        val bAbout: Button = findViewById(R.id.b_about)

        // Переход на экран истории с передачей текущего результата
        bHistory.setOnClickListener {
            playClickSound()
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("EXTRA_LAST_RESULT", result.text.toString())
            startActivity(intent)
        }

        // Переход на экран "О программе" (третий Activity)
        bAbout.setOnClickListener {
            playClickSound()
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        // Вспомогательная функция, чтобы текст добавлялся и экран прокручивался вправо
        fun appendText(value: String) {
            playClickSound()
            operation.append(value)
            // Автоматическая прокрутка в самый конец введённого текста
            scrollViewOperation.post {
                scrollViewOperation.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
            }
        }

        // 2. Инженерные функции и константы
        val b_ln: TextView = findViewById(R.id.b_ln)
        val b_cos: TextView = findViewById(R.id.b_cos)
        val b_sin: TextView = findViewById(R.id.b_sin)
        val b_pi: TextView = findViewById(R.id.b_pi)
        val b_e: TextView = findViewById(R.id.b_e)
        val b_sqrt: TextView = findViewById(R.id.b_sqrt)
        val b_log2: TextView = findViewById(R.id.b_log2)
        val b_pow: TextView = findViewById(R.id.b_pow)

        // 3. Цифры и точка
        val b_0: TextView = findViewById(R.id.b_0)
        val b_1: TextView = findViewById(R.id.b_1)
        val b_2: TextView = findViewById(R.id.b_2)
        val b_3: TextView = findViewById(R.id.b_3)
        val b_4: TextView = findViewById(R.id.b_4)
        val b_5: TextView = findViewById(R.id.b_5)
        val b_6: TextView = findViewById(R.id.b_6)
        val b_7: TextView = findViewById(R.id.b_7)
        val b_8: TextView = findViewById(R.id.b_8)
        val b_9: TextView = findViewById(R.id.b_9)
        val b_triple_zero: TextView = findViewById(R.id.b_triple_zero)
        val b_dot: TextView = findViewById(R.id.b_dot)

        // 4. Операции и скобки
        val b_add: TextView = findViewById(R.id.b_add)
        val b_sub: TextView = findViewById(R.id.b_sub)
        val b_mul: TextView = findViewById(R.id.b_mul)
        val b_div: TextView = findViewById(R.id.b_div)
        val b_percent: TextView = findViewById(R.id.b_percent)
        val b_leftb: TextView = findViewById(R.id.b_leftb)
        val b_rightb: TextView = findViewById(R.id.b_rightb)

        // 5. Управление
        val b_back: TextView = findViewById(R.id.b_back)
        val b_clear: TextView = findViewById(R.id.b_AC)
        val b_equal: TextView = findViewById(R.id.b_equal)


        // --- НАЗНАЧЕНИЕ КЛИКОВ ---

        b_0.setOnClickListener { appendText("0") }
        b_1.setOnClickListener { appendText("1") }
        b_2.setOnClickListener { appendText("2") }
        b_3.setOnClickListener { appendText("3") }
        b_4.setOnClickListener { appendText("4") }
        b_5.setOnClickListener { appendText("5") }
        b_6.setOnClickListener { appendText("6") }
        b_7.setOnClickListener { appendText("7") }
        b_8.setOnClickListener { appendText("8") }
        b_9.setOnClickListener { appendText("9") }
        b_triple_zero.setOnClickListener { appendText("000") }
        b_dot.setOnClickListener { appendText(".") }

        b_add.setOnClickListener { appendText("+") }
        b_sub.setOnClickListener { appendText("-") }
        b_mul.setOnClickListener { appendText("*") }
        b_div.setOnClickListener { appendText("/") }
        b_percent.setOnClickListener { appendText("%") }
        b_leftb.setOnClickListener { appendText("(") }
        b_rightb.setOnClickListener { appendText(")") }

        b_sqrt.setOnClickListener { appendText("sqrt(") }
        b_log2.setOnClickListener { appendText("log2(") }
        b_ln.setOnClickListener { appendText("log(") }
        b_sin.setOnClickListener { appendText("sin(") }
        b_cos.setOnClickListener { appendText("cos(") }
        b_pow.setOnClickListener { appendText("^") }
        b_pi.setOnClickListener { appendText("pi") }
        b_e.setOnClickListener { appendText("e") }

        // Кнопка удаления последнего символа (исправлен минус)
        b_back.setOnClickListener {
            playClickSound()
            val s = operation.text.toString()
            if (s.isNotEmpty()) {
                operation.text = s.substring(0, s.length - 1)
            }
        }

        // Кнопка полной очистки (AC)
        b_clear.setOnClickListener {
            playClickSound()
            operation.text = ""
            result.text = ""
        }

        fun preprocessPercentage(expression: String): String {
            var result = expression

            // 1. Обработка сложения и вычитания с процентами (например: 80-50% -> 80-(80*50/100))
            val plusMinusRegex = Regex("([0-9.]+)\\s*([+\\-])\\s*([0-9.]+)%")
            while (plusMinusRegex.containsMatchIn(result)) {
                result = result.replace(plusMinusRegex) { matchResult ->
                    val base = matchResult.groupValues[1]
                    val op = matchResult.groupValues[2]
                    val percent = matchResult.groupValues[3]
                    "$base$op($base*$percent/100)"
                }
            }

            // 2. Обработка остальных процентов (например: 50% -> (50/100))
            val singlePercentRegex = Regex("([0-9.]+)%")
            result = result.replace(singlePercentRegex) { matchResult ->
                val num = matchResult.groupValues[1]
                "($num/100)"
            }

            return result
        }

        // Кнопка вычисления (=)
        b_equal.setOnClickListener {
            playClickSound()
            try {
                val txt = operation.text.toString()
                if (txt.isNotEmpty()) {
                    val processedTxt = preprocessPercentage(txt)
                    val expression = ExpressionBuilder(processedTxt).build()
                    val calcResult = expression.evaluate()

                    val resString = if (calcResult == calcResult.toLong().toDouble()) {
                        calcResult.toLong().toString()
                    } else {
                        calcResult.toString()
                    }

                    result.text = resString

                    // Сохраняем в историю (например: "80 - 50% = 40")
                    HistoryManager.addCalculation("$txt = $resString")
                }
            } catch (e: Exception) {
                result.text = "Error"
            }
        }

        // Клик по результату переносит его в строку ввода
        result.setOnClickListener {
            val restext = result.text.toString()
            if (restext != "Error" && restext.isNotEmpty()) {
                operation.text = restext
                result.text = ""
            }
        }

        // Отключаем встроенные системные звуки Android для всех кнопок и экранов ввода,
        // чтобы они не издавали звук сами по себе
        val clickableViews = listOf(
            b_0, b_1, b_2, b_3, b_4, b_5, b_6, b_7, b_8, b_9,
            b_triple_zero, b_dot, b_add, b_sub, b_mul, b_div,
            b_percent, b_leftb, b_rightb, b_sqrt, b_log2, b_ln,
            b_sin, b_sin, b_cos, b_pow, b_pi, b_e, b_back, b_clear,
            b_equal, bHistory, bAbout, bAbout, result
        )
        for (view in clickableViews) {
            view.isSoundEffectsEnabled = false
        }
    }
    override fun onStart() { super.onStart(); Log.d(TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }
}
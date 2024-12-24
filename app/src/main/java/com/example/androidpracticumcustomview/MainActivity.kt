package com.example.androidpracticumcustomview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.androidpracticumcustomview.ui.theme.CustomContainer
import androidx.activity.compose.setContent
import com.example.androidpracticumcustomview.ui.theme.ComposeViewScreen

/*
Задание:
Реализуйте необходимые компоненты.
*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        Раскомментируйте нужный вариант
         */


        //startXmlPracticum() // «традиционный» android (XML)
        startComposePracticum() // Jetpack Compose

    }

    private fun startXmlPracticum() {
        val customContainer = CustomContainer(this)
        setContentView(customContainer)

        val firstView = TextView(this).apply {
            text = "firstView"
        }

        val secondView = TextView(this).apply {
            text = "secondView"
        }

        customContainer.addView(firstView)

        // Добавление второго элемента через некоторое время
        Handler(Looper.getMainLooper()).postDelayed({
            customContainer.addView(secondView)
        }, 2000)
    }

    private fun startComposePracticum(){
        setContent {
            ComposeViewScreen()
        }
    }

}
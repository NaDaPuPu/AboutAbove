package com.pupu.aboutabove

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val randomTheme = RandomTheme()
        randomTheme.aboveTheme(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
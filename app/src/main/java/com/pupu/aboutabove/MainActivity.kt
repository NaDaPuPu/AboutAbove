package com.pupu.aboutabove

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pupu.aboutabove.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val randomTheme = RandomTheme()
        randomTheme.aboveTheme(this)

        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        randomTheme.aboveScript(this, binding.textViewMainScript)
    }
}
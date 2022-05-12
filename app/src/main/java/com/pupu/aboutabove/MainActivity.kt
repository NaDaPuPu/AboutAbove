package com.pupu.aboutabove

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pupu.aboutabove.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val randomTheme = RandomTheme()
        randomTheme.aboveTheme(this)

        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        randomTheme.aboveScript(this, binding.textViewMainScript, binding.imageViewMainMember)

        binding.buttonMainTest.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.buttonMainTest -> {
                val intent = Intent(this, TestActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
                finish()
            }

            binding.buttonMainMember -> {

            }
        }
    }
}
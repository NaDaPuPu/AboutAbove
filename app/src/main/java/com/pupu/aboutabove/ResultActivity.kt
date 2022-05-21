package com.pupu.aboutabove

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pupu.aboutabove.databinding.ActivityResultBinding
import com.pupu.aboutabove.databinding.ActivityTestBinding

class ResultActivity : AppCompatActivity() {
    private var mBinding: ActivityResultBinding? = null
    private val binding get() = mBinding!!
    private val result = arrayOf("비경", "나다프프", "여우라온", "루도비코", "마녀록리")
//    (비경, 프프, 라온, 루도, 록리)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewResult.text = result[intent.getIntExtra("maxIdx", 0)]
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
        finish()
    }
}
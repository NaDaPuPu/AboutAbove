package com.pupu.aboutabove

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pupu.aboutabove.databinding.ActivityTestBinding

private const val NUM_PAGES = 10

class TestActivity : FragmentActivity(), View.OnClickListener {
    private var mBinding: ActivityTestBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        // View Binding
        super.onCreate(savedInstanceState)
        mBinding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pager Adapter
        val pagerAdapter = TestViewPagerAdapter(this)
        binding.viewPager2Test.adapter = pagerAdapter

        // Button
        binding.buttonTestBefore.setOnClickListener(this)
        binding.buttonTestAfter.setOnClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.buttonTestBefore -> {
                Log.d("test", "left")
            }

            binding.buttonTestAfter -> {
                Log.d("test", "right")
            }
        }
    }

    private inner class TestViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = TestFragment()
    }
}
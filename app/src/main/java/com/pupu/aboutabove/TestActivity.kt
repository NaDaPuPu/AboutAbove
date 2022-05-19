package com.pupu.aboutabove

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.transition.Visibility
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.pupu.aboutabove.databinding.ActivityTestBinding

private const val NUM_PAGES = 10

class TestActivity : FragmentActivity(), View.OnClickListener {
    private var mBinding: ActivityTestBinding? = null
    private val binding get() = mBinding!!

    // 질문, A 지문, B 지문
    private val question = listOf<Triple<String, String, String>>(
        Triple("나는 방송을 본다면 매운맛의 방송이 좋다.", "좋아!", "으엑!"),
        Triple("", "", ""),
        Triple("", "", ""),
        Triple("", "", ""),
        Triple("", "", ""),
        Triple("", "", ""),
        Triple("", "", ""),
        Triple("", "", ""),
        Triple("", "", ""),
        Triple("", "", ""),
    )

    // 선택 지문 A, B에 따라 각각 점수가 달라짐
    // Pair<(A선택, B선택)>, arrayOf(비경, 프프, 라온, 비경, 록리)
    private val score = listOf<Pair<Array<Int>, Array<Int>>>(
        Pair(arrayOf(), arrayOf()),
        Pair(arrayOf(), arrayOf()),
        Pair(arrayOf(), arrayOf()),
        Pair(arrayOf(), arrayOf()),
        Pair(arrayOf(), arrayOf()),
        Pair(arrayOf(), arrayOf()),
        Pair(arrayOf(), arrayOf()),
        Pair(arrayOf(), arrayOf()),
        Pair(arrayOf(), arrayOf()),
        Pair(arrayOf(), arrayOf()),
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        // View Binding
        super.onCreate(savedInstanceState)
        mBinding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pager Adapter
        val pagerAdapter = TestViewPagerAdapter(this)
        binding.viewPager2Test.adapter = pagerAdapter
        binding.viewPager2Test.isUserInputEnabled = false
        binding.viewPager2Test.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(pos: Int) {
                super.onPageSelected(pos)
                val fragment: Fragment? = supportFragmentManager.findFragmentByTag("android:switcher:" + binding.viewPager2Test + ":" + pos)

                when (pos) {
                    0 -> binding.buttonTestBefore.visibility = View.INVISIBLE
                    NUM_PAGES - 1 -> binding.buttonTestAfter.visibility = View.INVISIBLE
                    else -> {
                        binding.buttonTestBefore.visibility = View.VISIBLE
                        binding.buttonTestAfter.visibility = View.VISIBLE
                    }
                }
            }
        })

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
                binding.viewPager2Test.currentItem -= 1

            }

            binding.buttonTestAfter -> {
                Log.d("test", "right")
                binding.viewPager2Test.currentItem += 1
            }
        }
    }

    private inner class TestViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = TestFragment(question.get(position))
    }
}
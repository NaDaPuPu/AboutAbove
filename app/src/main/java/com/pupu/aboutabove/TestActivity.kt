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

class TestActivity : FragmentActivity(), View.OnClickListener, TestFragment.OnSelectEventListener {
    private var mBinding: ActivityTestBinding? = null
    private val binding get() = mBinding!!

    // 질문, A 지문, B 지문
    private val question = arrayOf<Triple<String, String, String>>(
        Triple("나는 방송을 본다면 매운맛의 방송이 좋다.", "좋아!", "으엑!"),
        Triple("메롱", "우씨 죽을래?", "응~ 무지개 반사~"),
        Triple("공포게임..좋아하세요..?", "네", "저리 가세요"),
        Triple("", "", ""),
        Triple("", "", ""),
        Triple("", "", ""),
        Triple("", "", ""),
        Triple("", "", ""),
        Triple("", "", ""),
        Triple("", "", ""),
    )

    // 선택 지문 A, B에 따라 각각 점수가 달라짐
    // Pair<(A선택, B선택)>, arrayOf(비경, 프프, 라온, 루도, 록리)
    private val score = arrayOf<Pair<IntArray, IntArray>>(
        Pair(intArrayOf(0, 1, 2, 1, 0), intArrayOf(2, 1, 0, 1, 2)), // 매운맛 OX
        Pair(intArrayOf(), intArrayOf()), // 메롱
        Pair(intArrayOf(1, 0, 2, 2, 0), intArrayOf(1, 2, 0, 0, 2)),
        Pair(intArrayOf(), intArrayOf()),
        Pair(intArrayOf(), intArrayOf()),
        Pair(intArrayOf(), intArrayOf()),
        Pair(intArrayOf(), intArrayOf()),
        Pair(intArrayOf(), intArrayOf()),
        Pair(intArrayOf(), intArrayOf()),
        Pair(intArrayOf(), intArrayOf()),
    )

    // <포지션 별 선택 여부, >
    private var result: Array<Pair<Boolean, IntArray>> = Array(NUM_PAGES) { Pair(false, intArrayOf(0, 0, 0, 0, 0)) }

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

        override fun createFragment(pos: Int): Fragment = TestFragment(question.get(pos), pos)
    }

    override fun onReceivedData(pos: Int, selected: Int) {
        when (selected) {
            -1 -> result[pos] = Pair(false, intArrayOf(0, 0, 0, 0, 0))
            else -> result[pos] = Pair(true, score[pos].toList()[selected])
        }
        Log.d("MainActivity", "Position : $pos Selected : $selected")
        Log.d("MainActivity", "Result: ${result[pos].first}, ${result[pos].second}")
    }
}
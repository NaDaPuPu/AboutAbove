package com.pupu.aboutabove

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
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
    private val question = arrayOf(
        Triple("나는 방송을 본다면 매운맛의 방송이 좋다.", "좋아!", "으엑!"),
        Triple("메롱", "우씨 죽을래?", "응~ 무지개 반사~"),
        Triple("공포게임..좋아하세요..?", "네", "저리 가세요"),
        Triple("나는 빡겜을 해서 랭크를 올리고 싶다.", "그님티?", "게임은 즐겜이지"),
        Triple("나는 기가 센 편이다.", "으르렁", "깨갱"),
        Triple("나는 해결사다.", "YES", "깽판칠 시간이다!"),
        Triple("어떤 사람이 좋아?", "친절한 사람", "솔직한 사람"),
        Triple("나는 다재다능한 사람이 좋다.", "오 님 좀 쩌는 듯", "재수없어"),
        Triple("남자 or 여자", "남자", "여자"),
        Triple("시청자랑 말을 잘해주는 스트리머가 좋다.", "좋다", "안해도 된다")
    )

    // 선택 지문 A, B에 따라 각각 점수가 달라짐
    // Pair<(A선택, B선택)>, arrayOf(비경, 프프, 라온, 루도, 록리)
    private val score = arrayOf(
        Pair(intArrayOf(0, 1, 2, 1, 0), intArrayOf(2, 1, 0, 1, 2)), // 매운맛 OX
        Pair(intArrayOf(2, 0, 2, 1, 0), intArrayOf(0, 2, 0, 1, 2)), // 메롱
        Pair(intArrayOf(1, 0, 2, 2, 0), intArrayOf(1, 2, 0, 0, 2)), // 공포게임
        Pair(intArrayOf(0, 1, 1, 2, 1), intArrayOf(2, 1, 1, 0, 1)), // 빡겜? 즐겜?
        Pair(intArrayOf(0, 1, 2, 0, 1), intArrayOf(2, 1, 0, 2, 1)), // 기가
        Pair(intArrayOf(2, 2, 1, 1, 0), intArrayOf(0, 0, 1, 1, 2)), // 해결사
        Pair(intArrayOf(2, 2, 0, 2, 0), intArrayOf(0, 0, 2, 0, 2)), // 친절, 솔직
        Pair(intArrayOf(1, 2, 1, 1, 0), intArrayOf(1, 0, 1, 1, 2)), // 다재다능
        Pair(intArrayOf(0, 2, 0, 2, 0), intArrayOf(2, 0, 2, 0, 2)), // 남, 여
        Pair(intArrayOf(2, 0, 1, 1, 1), intArrayOf(0, 2, 1, 1, 1))  // 말
    )

    // <포지션 별 선택 여부, >
    private var checkedAnswer: Array<Pair<Boolean, Int?>> = Array(NUM_PAGES) { Pair(false, null) }

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
                    NUM_PAGES - 1 -> binding.buttonTestAfter.icon = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_baseline_check_24)
                    else -> {
                        binding.buttonTestBefore.visibility = View.VISIBLE
                        binding.buttonTestAfter.icon = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_baseline_chevron_right_24)
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
                binding.viewPager2Test.currentItem -= 1
            }

            binding.buttonTestAfter -> {
                when (binding.viewPager2Test.currentItem) {
                    NUM_PAGES - 1 -> {
                        var result = intArrayOf(0, 0, 0, 0, 0)
                        for (i in 0 until NUM_PAGES) {
                            if (!checkedAnswer[i].first) {
                                Toast.makeText(this, "모든 항목을 선택해주세요. $i", Toast.LENGTH_SHORT).show()
                                return
                            }
                            for (j in 0 until 5) {
                                result[j] += score[i].toList()[checkedAnswer[i].second!!][j]
                            }
                        }

                        val maxIdx = result.indices.maxByOrNull { result[it] } ?: -1

                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra("maxIdx", maxIdx)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
                        finish()
                    }
                    else -> binding.viewPager2Test.currentItem += 1
                }

            }
        }
    }

    private inner class TestViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(pos: Int): Fragment = TestFragment(question[pos], checkedAnswer[pos].second, pos)
    }

    override fun onReceivedData(pos: Int, selected: Int) {
        when (selected) {
            -1 -> checkedAnswer[pos] = Pair(false, null)
            else -> checkedAnswer[pos] = Pair(true, selected)
        }
        Log.d("Receive", "${checkedAnswer[pos].second}")
    }
}

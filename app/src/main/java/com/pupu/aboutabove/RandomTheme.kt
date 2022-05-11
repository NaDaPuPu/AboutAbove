package com.pupu.aboutabove

import android.content.Context
import java.util.*

class RandomTheme {
    fun aboveTheme(context: Context) {
        val random = Random()
        val num = random.nextInt(6)

        when (num) {
            0 -> context.setTheme(R.style.Theme_AboutAbove_Baekhoon)
            1 -> context.setTheme(R.style.Theme_AboutAbove_Beekyung)
            2 -> context.setTheme(R.style.Theme_AboutAbove_PuPu)
            3 -> context.setTheme(R.style.Theme_AboutAbove_Raon)
            4 -> context.setTheme(R.style.Theme_AboutAbove_Ludovico)
            5 -> context.setTheme(R.style.Theme_AboutAbove_RockLee)
        }
    }
}
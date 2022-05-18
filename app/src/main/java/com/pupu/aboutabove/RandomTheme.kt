package com.pupu.aboutabove

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class RandomTheme {
    val random = Random()
    val num = random.nextInt(5)

    fun aboveTheme(context: Context) {
        when (num) {
            0 -> context.setTheme(R.style.Theme_AboutAbove_Beekyung)
            1 -> context.setTheme(R.style.Theme_AboutAbove_PuPu)
            2 -> context.setTheme(R.style.Theme_AboutAbove_Raon)
            3 -> context.setTheme(R.style.Theme_AboutAbove_Ludovico)
            4 -> context.setTheme(R.style.Theme_AboutAbove_RockLee)
        }
    }

    fun aboveScript(context: Context, textView: TextView, imageView: ImageView) {
        val script = random.nextInt(3)
        var member = ""
        when (num) {
            0 -> member = "beekyung"
            1 -> member = "pupu"
            2 -> member = "raon"
            3 -> member = "ludovico"
            4 -> member = "rocklee"
        }
        val resId = context.resources.getIdentifier("main_${member}_${script}", "string", context.packageName)
        textView.setText(context.getString(resId))
        val resIdImg = context.resources.getIdentifier("${member}_0", "drawable", context.packageName)
        imageView.setImageResource(resIdImg)
    }
}
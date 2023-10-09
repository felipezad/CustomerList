package com.interview.customerlist.customers

import com.interview.customerlist.R
import javax.inject.Inject

typealias Background = Int
typealias Letter = Int

class CustomerStickColor @Inject constructor() {
    fun getColors(sticker: String): Pair<Letter, Background> {
        return when (sticker.lowercase()) {
            "ban" -> R.color.darker_red to R.color.light_red
            else -> R.color.darker_gray to R.color.light_gray
        }
    }
}
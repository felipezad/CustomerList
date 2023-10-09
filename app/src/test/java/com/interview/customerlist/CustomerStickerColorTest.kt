package com.interview.customerlist

import com.interview.customerlist.customers.CustomerStickColor
import org.junit.Assert.assertTrue
import org.junit.Test

class CustomerStickerColorTest {

    private val test = CustomerStickColor()

    @Test
    fun `Each Sticker should return their Colours`(){
        val defaultColor = test.getColors("any Value")

        assertTrue(defaultColor.first == 2131034160)
        assertTrue(defaultColor.second == 2131034210)

        val banColor = test.getColors("ban")

        assertTrue(banColor.first == 2131034161)
        assertTrue(banColor.second == 2131034211)
    }
}
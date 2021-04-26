package com.example.shoujiedemo.kotlinbook.view

import java.lang.Math.max

class Image(val width: Int,val height: Int, val flag: Int) {

    fun imageMax(w: Int = width, h: Int = height): Int {
        return max(w,h)
    }
}
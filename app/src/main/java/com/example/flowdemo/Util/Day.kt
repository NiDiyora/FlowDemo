package com.example.flowdemo.Util

enum class Day {
    SUNDAY,
    MONDAY,
    TUESDAY,
    THUSDAY,
    FRIDAY,
    SATURDAY;

    fun printdayformetted():String{
        return "Day of $this"
    }
}
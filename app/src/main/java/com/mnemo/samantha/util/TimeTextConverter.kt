package com.mnemo.samantha.util

class TimeTextConverter {

    companion object{

        fun convertTextToMinutes(timeText: String) : Int{
            return timeText.split(":")[0].toInt() * 60 + timeText.split(":")[1].toInt()
        }
    }
}
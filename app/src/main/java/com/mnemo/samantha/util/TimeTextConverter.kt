package com.mnemo.samantha.util

import java.text.SimpleDateFormat

class TimeTextConverter {

    companion object{
        fun convertMinutesToText(){

        }

        fun convertTextToMinutes(timeText: String) : Int{
            return timeText.split(":")[0].toInt() * 60 + timeText.split(":")[1].toInt()
        }
    }
}
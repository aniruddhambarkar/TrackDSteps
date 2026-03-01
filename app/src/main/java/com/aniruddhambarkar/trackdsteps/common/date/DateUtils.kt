package com.aniruddhambarkar.trackdsteps.common.date

import java.text.SimpleDateFormat

class DateUtils {
    fun getDateInDayDateTimeFormat(time: String){
        val format  = SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss")
        val date = format.parse(time)

    }
}
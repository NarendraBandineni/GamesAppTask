package com.example.gamesappnewtask.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object{
        fun getDateInDDMMYYYY(date:String):String{
            var finalDate:String = ""
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(date)
            val sdfOut = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
            sdf.let {
                finalDate= it?.let { it1 -> sdfOut.format(it1) }.toString()
            }
            return finalDate
        }
    }
}
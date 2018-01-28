package com.phonepe.notely.utils

import android.databinding.BindingAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Kumar Gaurav on 1/25/2018.
 *
 * A date formatter class to show date in user understandable format.
 */
class DateFormatter {

    companion object {

        /*
        * method to get formatted date
        *
        * @param date is a date string which is to be formatted
        *
        * */
        fun getFormattedDate(date: String): String{
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
            cal.setTime(sdf.parse(date))

            val today = Calendar.getInstance()

            val todaysDate = today.get(Calendar.DAY_OF_MONTH)
            val todaysMonth = today.get(Calendar.MONTH)
            val todaysYear = today.get(Calendar.YEAR)
            val todaysWeek = today.get(Calendar.WEEK_OF_YEAR)

            val calDate = cal.get(Calendar.DAY_OF_MONTH)
            val calMonth = cal.get(Calendar.MONTH)
            val calYear = cal.get(Calendar.YEAR)
            val calWeek = cal.get(Calendar.WEEK_OF_YEAR)

            if(todaysDate == calDate && todaysMonth == calMonth && todaysYear == calYear){
                return "Today at " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.SECOND) + " " +  getAMOrPM(cal.get(Calendar.AM_PM))
            }else if(calWeek == todaysWeek){
                return getDayName(cal.get(Calendar.DAY_OF_WEEK)) + " at " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.SECOND) + " " + getAMOrPM(cal.get(Calendar.AM_PM))
            }
            return getMonthName(cal.get(Calendar.MONTH)) + " " + cal.get(Calendar.DAY_OF_MONTH) + " at " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.SECOND) + " " + getAMOrPM(cal.get(Calendar.AM_PM))
        }

        /*
        * to get name of the day from day integer
        *
        * @param day which name to be fetched
        *
        * */
        private fun getDayName(day: Int):String {
            when (day) {
                1 -> return "Sunday"
                2 -> return "Monday"
                3 -> return "Tuesday"
                4 -> return "Wednesday"
                5 -> return "Thursday"
                6 -> return "Friday"
                7 -> return "Saturday"
            }
            return ""
        }

        /*
        * to get name of the month from month integer
        *
        * @param month which name to be fetched
        *
        * */
        private fun getMonthName(month: Int):String {
            when (month) {
                0 -> return "Jan"
                1 -> return "Feb"
                2 -> return "Mar"
                3 -> return "Apr"
                4 -> return "May"
                5 -> return "Jun"
                6 -> return "Jul"
                7 -> return "Aug"
                8 -> return "Sep"
                9 -> return "Oct"
                10 -> return "Nov"
                11 -> return "Dec"
            }
            return ""
        }

        /*
        * to get AM or PM string
        *
        * @param AM_OR_PM
        *
        * */
        private fun getAMOrPM(AM_OR_PM: Int): String{
            when(AM_OR_PM){
                0 -> return "AM"
                1 -> return "PM"
            }
            return ""
        }
    }
}
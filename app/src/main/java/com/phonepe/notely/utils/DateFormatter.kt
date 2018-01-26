package com.phonepe.notely.utils

import android.databinding.BindingAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Kumar Gaurav on 1/25/2018.
 *
 * A date formatter class to show date in used understandable format.
 */
class DateFormatter {

    companion object {

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
            return getMonthName(cal.get(Calendar.MONTH)) + " at " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.SECOND) + " " + getAMOrPM(cal.get(Calendar.AM_PM))
        }

        @JvmStatic @BindingAdapter("formattedDate")
        fun getAndSetFormattedDate(view: TextView, date: String?){
            if(date != null)
                view.setText(getFormattedDate(date))
        }

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

        private fun getMonthName(month: Int):String {
            when (month) {
                1 -> return "Jan"
                2 -> return "Feb"
                3 -> return "Mar"
                4 -> return "Apr"
                5 -> return "May"
                6 -> return "Jun"
                7 -> return "Jul"
                8 -> return "Aug"
                9 -> return "Sep"
                10 -> return "Oct"
                11 -> return "Nov"
                12 -> return "Dec"
            }
            return ""
        }

        private fun getAMOrPM(AM_OR_PM: Int): String{
            when(AM_OR_PM){
                0 -> return "AM"
                1 -> return "PM"
            }
            return ""
        }
    }
}
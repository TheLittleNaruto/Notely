package com.phonepe.notely.ui.binding

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.widget.TextView
import com.phonepe.notely.utils.DateFormatter

/**
 * Created by Kumar Gaurav on 1/28/2018.
 */
class DateConverterBindingAdapter {

    companion object {
        @JvmStatic @BindingAdapter("formatDate")
        fun convertDateAndSet(textView: TextView, date: String){
            if(!TextUtils.isEmpty(date)){
                textView.setText(DateFormatter.getFormattedDate(date))
            }
        }
    }

}
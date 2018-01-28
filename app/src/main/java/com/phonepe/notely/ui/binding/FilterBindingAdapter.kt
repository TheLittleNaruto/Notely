package com.phonepe.notely.ui.binding

import android.content.Context
import android.databinding.BindingAdapter
import android.widget.ListView
import com.phonepe.notely.ui.adapter.FilterAdapter
import com.phonepe.notely.ui.model.Filter

/**
 * Created by Kumar Gaurav on 1/28/2018.
 */
class FilterBindingAdapter {

    companion object {
        @JvmStatic @BindingAdapter("adapter")
        fun setFilterAdapter(listView: ListView, context: Context){
            listView.adapter = FilterAdapter(context, Filter.getFilterList())
        }
    }
}
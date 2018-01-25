package com.phonepe.notely.ui.model

import android.text.TextUtils
import android.widget.ListView
import android.util.SparseBooleanArray



/**
 * Created by Kumar Gaurav on 1/22/2018.
 */
class Filter() {

    companion object {

        fun getFilterList():MutableList<String>{
            val filters = mutableListOf<String>()

            filters.add("Hearted")
            filters.add("Favorite")
            filters.add("Poems")
            filters.add("Story")

            return filters
        }

        //to get list of checked filter item
        fun getCheckedItems(listView: ListView):String{
            var checkedItems: String = ""
            val len = listView.count
            val checked = listView.checkedItemPositions
            for (i in 0 until len)
                if (checked.get(i)) {
                   if(TextUtils.isEmpty(checkedItems)){
                       checkedItems = getFilterList().get(i)
                   }else{
                       checkedItems = checkedItems + "," + getFilterList().get(i)
                   }

                }
            return checkedItems
        }
    }



}
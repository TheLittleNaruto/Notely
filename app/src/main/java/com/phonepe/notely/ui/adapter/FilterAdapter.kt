package com.phonepe.notely.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import com.phonepe.notely.R
import com.phonepe.notely.ui.model.Filter

/**
 * Created by Kumar Gaurav on 1/24/2018.
 * Filter Adapter to show list of filters
 */

class FilterAdapter(private val mContext: Context, private val mFilters: List<String>) : ArrayAdapter<String>(mContext, R.layout.filter_item, mFilters) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.filter_item, parent, false)
            holder = ViewHolder()
            holder.filter = convertView!!.findViewById(R.id.filter)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val filter = mFilters[position]
        holder.filter!!.text = filter
        return convertView
    }

    internal class ViewHolder {
        var filter: TextView? = null
    }
}

package com.phonepe.notely.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.phonepe.notely.R
import com.phonepe.notely.databinding.FilterItemBinding
import android.databinding.DataBindingUtil.inflate

/**
 * Created by Kumar Gaurav on 1/24/2018.
 * Filter Adapter to show list of filters
 */

class FilterAdapter(private val mContext: Context, private val mFilters: List<String>) : ArrayAdapter<String>(mContext, R.layout.filter_item, mFilters) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(parent.context)
        val binding: FilterItemBinding = inflate(inflater, R.layout.filter_item, parent, false)
        binding.setFilter(mFilters[position])
        return binding.root
    }
}

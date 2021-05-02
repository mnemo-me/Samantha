package com.mnemo.samantha.ui.monthly_schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.MonthlyScheduleDayBinding

class MonthlyScheduleAdapter: BaseAdapter() {

    var days = mutableListOf<String>()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun getCount(): Int  = days.size

    override fun getItem(position: Int): Any = days[position]

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var convertView = convertView
        val binding: MonthlyScheduleDayBinding

        if (convertView == null) {

            val layoutInflater = LayoutInflater.from(parent?.context)
            binding = DataBindingUtil.inflate(layoutInflater, R.layout.monthly_schedule_day, parent, false)
            convertView = binding.root

            binding.monthScheduleDayDate.text = days[position]
        }

        return convertView
    }


}
package com.mnemo.samantha.ui.create_profile.create_schedule

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.databinding.DayOfWeekBinding
import java.text.SimpleDateFormat
import java.util.*

class DayOfWeekAdapter : ListAdapter<Int, DayOfWeekAdapter.ViewHolder>(DayOfWeekDiffCallback()) {

    private val workingDays = SparseBooleanArray(7)

    val workingDaysOfWeek: List<Int>
    get() {
        val workingDaysOfWeek = mutableListOf<Int>()

        workingDays.forEach {key, value ->
            if (value) workingDaysOfWeek.add(getItem(key))
        }

        return workingDaysOfWeek
    }

    private val selectDayOfWeekClickListener = SelectDayOfWeekClickListener{position ->
        workingDays.append(position, !workingDays.get(position))
    }

    fun submitListWithCheck(daysOfWeek: List<Int>){

        daysOfWeek.forEachIndexed{index, element ->
            if (element != 1 && element != 7){
                workingDays.append(index, true)
            }
        }

        submitList(daysOfWeek)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position, workingDays[position], selectDayOfWeekClickListener)
    }

    class ViewHolder private constructor(val binding: DayOfWeekBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(dayOfWeek: Int, position: Int, isWorkingDay: Boolean, selectDayOfWeekClickListener: SelectDayOfWeekClickListener){

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)
            binding.dayOfWeek.text = SimpleDateFormat("E").format(calendar.time)

            binding.root.isSelected = isWorkingDay

            binding.root.setOnClickListener{

                binding.root.isSelected = !binding.root.isSelected
                selectDayOfWeekClickListener.onClick(position)
            }
        }

         companion object{
             fun from(parent: ViewGroup) : ViewHolder{

                 val layoutInflater = LayoutInflater.from(parent.context)
                 val binding = DayOfWeekBinding.inflate(layoutInflater, parent, false)

                 return ViewHolder(binding)
             }
         }
    }

    // DiffUtil callback
    class DayOfWeekDiffCallback : DiffUtil.ItemCallback<Int>(){
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return  oldItem == newItem
        }
    }

    // Click listener
    class SelectDayOfWeekClickListener(val clickListener: (position: Int) -> Unit){
        fun onClick(position: Int) = clickListener(position)
    }

}
package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.databinding.AppointmentBinding
import com.mnemo.samantha.databinding.DayScheduleHeaderBinding
import com.mnemo.samantha.repository.database.entity.Appointment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class DayScheduleAdapter : ListAdapter<DayScheduleAdapter.DataItem, RecyclerView.ViewHolder>(AppointmentDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(appointments : List<Appointment>?){
        adapterScope.launch {
            val items = when(appointments){
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + appointments.map { DataItem.AppointmentItem(it) }
            }
            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER -> HeaderHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder){
            is HeaderHolder -> {
                holder.bind()
            }

            is ViewHolder -> {
                val appointmentItem = getItem(position) as DataItem.AppointmentItem
                holder.bind(appointmentItem.appointment)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position){
            0 -> ITEM_VIEW_TYPE_HEADER
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }


    class ViewHolder private constructor(val binding: AppointmentBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(appointment: Appointment){

            binding.appointmentClientAvatar.clipToOutline = true
        }

        companion object{
            fun from(parent: ViewGroup) : ViewHolder{

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AppointmentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class HeaderHolder private constructor(val binding: DayScheduleHeaderBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(){

        }

        companion object{
            fun from(parent: ViewGroup) : HeaderHolder{

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DayScheduleHeaderBinding.inflate(layoutInflater, parent, false)

                return HeaderHolder(binding)
            }
        }
    }



    sealed class DataItem{

        abstract val id : Long

        data class AppointmentItem(val appointment: Appointment) : DataItem(){
            override val id = appointment.appointmentId
        }

        object Header : DataItem(){
            override val id = Long.MIN_VALUE
        }
    }

    class AppointmentDiffCallback : DiffUtil.ItemCallback<DataItem>(){
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}
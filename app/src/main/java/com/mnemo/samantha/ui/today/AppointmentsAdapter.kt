package com.mnemo.samantha.ui.today

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.databinding.TodayClientBinding
import com.mnemo.samantha.databinding.TodayClientsHeaderBinding
import com.mnemo.samantha.domain.entities.Appointment
import com.mnemo.samantha.domain.entities.convertToString
import com.mnemo.samantha.ui.loadImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class AppointmentsAdapter: ListAdapter<AppointmentsAdapter.DataItem, RecyclerView.ViewHolder>(AppointmentDiffCallback()){

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    lateinit var phoneCallClickListener: PhoneCallClickListener
    lateinit var dateText: String
    lateinit var pictureFolder: File

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
                holder.bind(dateText)
            }

            is ViewHolder -> {
                val appointmentItem = getItem(position) as DataItem.AppointmentItem
                holder.bind(appointmentItem.appointment, phoneCallClickListener, pictureFolder)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position){
            0 -> ITEM_VIEW_TYPE_HEADER
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }


    class ViewHolder private constructor(val binding: TodayClientBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(appointment: Appointment, phoneCallClickListener: PhoneCallClickListener, pictureFolder: File){

            binding.appointment = appointment
            binding.todayClientServices.text = appointment.services?.convertToString()
            binding.phoneCallClickListener = phoneCallClickListener

            binding.todayClientAvatar.clipToOutline = true
            binding.todayClientPhoneCall.visibility = if (appointment.client?.phoneNumber != "") View.VISIBLE else View.INVISIBLE

            binding.todayClientAvatar.loadImage(File(pictureFolder, "cl${appointment.client?.id}.JPEG"))
        }


        companion object{
            fun from(parent: ViewGroup) : ViewHolder{

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TodayClientBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class HeaderHolder private constructor(val binding: TodayClientsHeaderBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(dateText: String){
            binding.todayClientsHeaderTitle.text = dateText
        }

        companion object{
            fun from(parent: ViewGroup) : HeaderHolder{

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TodayClientsHeaderBinding.inflate(layoutInflater, parent, false)

                return HeaderHolder(binding)
            }
        }
    }



    sealed class DataItem{

        abstract val id : Long

        data class AppointmentItem(val appointment: Appointment) : DataItem(){
            override val id = appointment.id
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


    // Button click listener
    class PhoneCallClickListener(val clickListener: (clientPhoneNumber: String) -> Unit){
        fun onClick(clientPhoneNumber : String) = clickListener(clientPhoneNumber)
    }
}
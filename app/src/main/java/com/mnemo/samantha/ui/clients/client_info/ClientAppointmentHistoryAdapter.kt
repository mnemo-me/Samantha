package com.mnemo.samantha.ui.clients.client_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.ClientAppointmentHistoryItemBinding
import com.mnemo.samantha.domain.Appointment
import com.mnemo.samantha.domain.convertToString
import java.text.SimpleDateFormat
import java.util.*

class ClientAppointmentHistoryAdapter: ListAdapter<Appointment, ClientAppointmentHistoryAdapter.ViewHolder>(ClientAppointmentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(val binding: ClientAppointmentHistoryItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(appointment: Appointment){
            binding.appointment = appointment

            val calendar = Calendar.getInstance()
            val currentTime = calendar.time

            calendar.set(appointment.year, appointment.month, appointment.date, appointment.time.div(60), appointment.time.rem(60))
            val appointmentTime = calendar.time

            binding.clientAppointmentHistoryDate.text = SimpleDateFormat("dd-MM-yyyy").format(calendar.time)

            binding.clientAppointmentHistoryServices.text = appointment.services?.convertToString()

            if (appointmentTime > currentTime) {
                binding.clientAppointmentHistoryTime.setTextColor(binding.root.resources.getColor(R.color.primaryColor))
                binding.clientAppointmentHistoryDate.setTextColor(binding.root.resources.getColor(R.color.primaryColor))
                binding.clientAppointmentHistoryServices.setTextColor(binding.root.resources.getColor(R.color.primaryColor))
            }else{
                binding.clientAppointmentHistoryTime.setTextColor(binding.root.resources.getColor(android.R.color.tab_indicator_text))
                binding.clientAppointmentHistoryDate.setTextColor(binding.root.resources.getColor(android.R.color.tab_indicator_text))
                binding.clientAppointmentHistoryServices.setTextColor(binding.root.resources.getColor(android.R.color.tab_indicator_text))
            }
        }

        companion object{
            fun from(parent: ViewGroup) : ViewHolder{

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ClientAppointmentHistoryItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    // DiffUtil callback
    class ClientAppointmentDiffCallback : DiffUtil.ItemCallback<Appointment>(){
        override fun areItemsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
            return oldItem == newItem
        }
    }
}
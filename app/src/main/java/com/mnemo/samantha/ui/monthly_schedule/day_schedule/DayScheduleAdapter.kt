package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.AppointmentBinding
import com.mnemo.samantha.databinding.DayScheduleHeaderBinding
import com.mnemo.samantha.repository.database.entity.Appointment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

private const val APPOINTMENT_STATE_BREAK = -1
private const val APPOINTMENT_STATE_FREE = 0
private const val APPOINTMENT_STATE_BUSY = 1

class DayScheduleAdapter : ListAdapter<DayScheduleAdapter.DataItem, RecyclerView.ViewHolder>(AppointmentDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)


    lateinit var addClientClickListener : AddClientClickListener
    lateinit var buttonClickListener: ButtonClickListener
    lateinit var editScheduleClickListener : EditScheduleClickListener


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
                holder.bind(editScheduleClickListener)
            }

            is ViewHolder -> {
                val appointmentItem = getItem(position) as DataItem.AppointmentItem
                holder.bind(appointmentItem.appointment, addClientClickListener, buttonClickListener)
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

        fun bind(appointment: Appointment, addClientClickListener: AddClientClickListener, buttonClickListener: ButtonClickListener){

            binding.appointment = appointment
            binding.addClientClickListener = addClientClickListener

            binding.appointmentClientAvatar.clipToOutline = true
            binding.appointmentTime.text = appointment.appointmentTime.toString()

            // Appointment state
            val appointmentState = when(appointment.appointmentClient){
                null -> APPOINTMENT_STATE_BREAK
                0L -> APPOINTMENT_STATE_FREE
                else -> APPOINTMENT_STATE_BUSY
            }

            initAppointmentState(appointmentState)

            binding.appointmentClientButton.setOnClickListener(View.OnClickListener {

                when(appointmentState){
                    APPOINTMENT_STATE_BREAK -> {
                        initAppointmentState(APPOINTMENT_STATE_FREE)
                        buttonClickListener.onClick(appointment.appointmentId, 0L)
                    }
                    APPOINTMENT_STATE_FREE -> {
                        initAppointmentState(APPOINTMENT_STATE_BREAK)
                        buttonClickListener.onClick(appointment.appointmentId, null)
                    }
                    APPOINTMENT_STATE_BUSY -> {
                        initAppointmentState(APPOINTMENT_STATE_FREE)
                        buttonClickListener.onClick(appointment.appointmentId, 0L)
                    }
                }
            })
        }

        // Setup view based on appointment state
        private fun initAppointmentState(appointmentState: Int){
            when (appointmentState){
                APPOINTMENT_STATE_BREAK -> {
                    binding.appointmentBackground.setBackgroundResource(R.color.grey_background)
                    binding.appointmentClientAvatar.visibility = View.INVISIBLE
                    binding.appointmentClientName.text = ""
                    binding.appointmentClientButton.setImageResource(R.drawable.outline_add_white_24)
                }
                APPOINTMENT_STATE_FREE -> {
                    binding.appointmentBackground.setBackgroundResource(R.color.blue_background)
                    binding.appointmentClientAvatar.visibility = View.VISIBLE
                    binding.appointmentClientAvatar.setImageResource(R.drawable.outline_account_circle_white_24)
                    binding.appointmentClientName.setText(R.string.add_client)
                    binding.appointmentClientButton.setImageResource(R.drawable.outline_remove_white_24)
                }

                APPOINTMENT_STATE_BUSY -> {
                    binding.appointmentBackground.setBackgroundResource(R.color.white)
                    binding.appointmentClientAvatar.visibility = View.VISIBLE
                    binding.appointmentClientAvatar.setImageResource(R.drawable.samantha)
                    //binding.appointmentClientName.setText(R.string.add_client)
                    binding.appointmentClientButton.setImageResource(R.drawable.outline_close_black_24)
                }
            }
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

        fun bind(editScheduleClickListener: EditScheduleClickListener){
            binding.editScheduleClickListener = editScheduleClickListener
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


    // Click listeners
    class AddClientClickListener(val clickListener: (appointmentId: Long) -> Unit){
        fun onClick(appointment: Appointment) = clickListener(appointment.appointmentId)
    }

    class ButtonClickListener(val clickListener: (appointmentId: Long, clientId : Long?) -> Unit){
        fun onClick(appointmentId: Long, clientId: Long?) = clickListener(appointmentId, clientId)
    }

    class EditScheduleClickListener(val clickListener: () -> Unit){
        fun onClick() = clickListener()
    }
}
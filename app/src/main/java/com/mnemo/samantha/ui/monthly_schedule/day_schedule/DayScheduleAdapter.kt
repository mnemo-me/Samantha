package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.AppointmentBinding
import com.mnemo.samantha.databinding.DayScheduleHeaderBinding
import com.mnemo.samantha.domain.*
import com.mnemo.samantha.domain.entities.*
import com.mnemo.samantha.ui.loadImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1


open class DayScheduleAdapter : ListAdapter<DayScheduleAdapter.DataItem, RecyclerView.ViewHolder>(AppointmentDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    lateinit var clickListener : ClickListener
    lateinit var buttonClickListener: ButtonClickListener
    lateinit var editScheduleClickListener : EditScheduleClickListener
    lateinit var pictureFolder: File

    lateinit var dateText: String

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
                holder.bind(dateText, editScheduleClickListener)
            }

            is ViewHolder -> {
                val appointmentItem = getItem(position) as DataItem.AppointmentItem
                holder.bind(appointmentItem.appointment, clickListener, buttonClickListener, pictureFolder)
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

        fun bind(appointment: Appointment, clickListener: ClickListener, buttonClickListener: ButtonClickListener, pictureFolder: File){

            binding.appointment = appointment
            binding.appointmentClientServices.text = appointment.services?.convertToString()
            binding.clickListener = clickListener

            binding.appointmentClientAvatar.clipToOutline = true

            initAppointmentState(appointment.state)

            // Bind client info
            if (appointment.state == APPOINTMENT_STATE_BUSY) {
                binding.appointmentClientAvatar.loadImage(File(pictureFolder, "cl${appointment.client?.id}.JPEG"))
            }

            // Button click listener
            binding.appointmentClientButton.setOnClickListener {

                when (appointment.state) {
                    APPOINTMENT_STATE_BREAK -> {
                        initAppointmentState(APPOINTMENT_STATE_FREE)
                        buttonClickListener.onClick(appointment.id, APPOINTMENT_STATE_FREE)
                    }
                    APPOINTMENT_STATE_FREE -> {
                        initAppointmentState(APPOINTMENT_STATE_BREAK)
                        buttonClickListener.onClick(appointment.id, APPOINTMENT_STATE_BREAK)
                    }
                    APPOINTMENT_STATE_BUSY -> {
                        initAppointmentState(APPOINTMENT_STATE_FREE)
                        buttonClickListener.onClick(appointment.id, APPOINTMENT_STATE_FREE)
                    }
                }
            }
        }

        // Setup view based on appointment state
        private fun initAppointmentState(appointmentState: Int){
            when (appointmentState){
                APPOINTMENT_STATE_BREAK -> {
                    binding.appointmentBackground.setBackgroundResource(R.color.paint_it_black)
                    binding.appointmentTime.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                    binding.appointmentClientAvatar.visibility = View.INVISIBLE
                    binding.appointmentClientAvatar.setImageResource(android.R.color.transparent)
                    binding.appointmentClientName.text = ""
                    binding.appointmentClientName.visibility = View.INVISIBLE
                    binding.appointmentClientServices.visibility = View.INVISIBLE
                    binding.appointmentAddClient.visibility = View.INVISIBLE
                    binding.appointmentClientButton.setImageResource(R.drawable.outline_add_white_24)
                }
                APPOINTMENT_STATE_FREE -> {
                    binding.appointmentBackground.setBackgroundResource(R.color.green)
                    binding.appointmentTime.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                    binding.appointmentClientAvatar.visibility = View.INVISIBLE
                    binding.appointmentClientAvatar.setImageResource(android.R.color.transparent)
                    binding.appointmentClientName.visibility = View.INVISIBLE
                    binding.appointmentClientServices.visibility = View.INVISIBLE
                    binding.appointmentAddClient.visibility = View.VISIBLE
                    binding.appointmentClientButton.setImageResource(R.drawable.outline_remove_white_24)
                }

                APPOINTMENT_STATE_BUSY -> {
                    binding.appointmentBackground.setBackgroundResource(R.color.white)
                    binding.appointmentTime.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.tab_indicator_text))
                    binding.appointmentClientAvatar.visibility = View.VISIBLE
                    binding.appointmentClientName.visibility = View.VISIBLE
                    binding.appointmentClientServices.visibility = View.VISIBLE
                    binding.appointmentAddClient.visibility = View.INVISIBLE
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

        fun bind(dateText: String, editScheduleClickListener: EditScheduleClickListener){
            binding.dayScheduleDay.text = dateText
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


    // Click listeners
    class ClickListener(val clickListener: (appointmentId: Long, appointmentState: Int) -> Unit){
        fun onClick(appointment: Appointment) = clickListener(appointment.id, appointment.state)
    }

    class ButtonClickListener(val clickListener: (appointmentId: Long, appointmentState : Int) -> Unit){
        fun onClick(appointmentId: Long, appointmentState: Int) = clickListener(appointmentId, appointmentState)
    }

    class EditScheduleClickListener(val clickListener: () -> Unit){
        fun onClick() = clickListener()
    }
}
package com.mnemo.samantha.ui.monthly_schedule.day_schedule

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentDayScheduleBinding
import com.mnemo.samantha.domain.entities.APPOINTMENT_STATE_FREE


class DayScheduleFragment : Fragment() {

    private lateinit var binding: FragmentDayScheduleBinding
    private lateinit var viewModel: DayScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_day_schedule, container, false)
        val view = binding.root
        val dividerItemDecoration = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
        binding.dayScheduleSchedule.addItemDecoration(dividerItemDecoration)

        // Get date parameters from bundle
        val year = requireArguments().getInt("year")
        val month = requireArguments().getInt("month")
        val date = requireArguments().getInt("date")


        // Create ViewModel via Factory and bind it to View
        val viewModelFactory = DayScheduleViewModelFactory(year, month, date)

        viewModel = ViewModelProvider(this, viewModelFactory).get(DayScheduleViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel


        // Create adapter for RecycleView
        val adapter = DayScheduleAdapter()
        adapter.pictureFolder = view.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        adapter.dateText = viewModel.dateText
        binding.dayScheduleSchedule.adapter = adapter

        // Adapter click listeners
        adapter.clickListener = DayScheduleAdapter.ClickListener{appointmentId, appointmentState ->
            if (appointmentState == APPOINTMENT_STATE_FREE) {
                view.findNavController().navigate(R.id.action_dayScheduleFragment_to_addClientDialogFragment, bundleOf("appointment_id" to appointmentId)
                )
            }
        }

        adapter.buttonClickListener = DayScheduleAdapter.ButtonClickListener {appointmentId, appointmentState ->
            viewModel.updateAppointmentState(appointmentId, appointmentState)
        }

        adapter.editScheduleClickListener = DayScheduleAdapter.EditScheduleClickListener {

        }

        viewModel.appointments.observe(viewLifecycleOwner, { appointments ->
            adapter.addHeaderAndSubmitList(appointments)
        })


        return view
    }

}
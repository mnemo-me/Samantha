package com.mnemo.samantha.ui.create_profile.create_schedule

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentCreateScheduleBinding


class CreateScheduleFragment : Fragment() {

    private lateinit var binding: FragmentCreateScheduleBinding
    private lateinit var viewModel: CreateScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_schedule, container, false)
        val view = binding.root

        // Get arguments
        val scheduleId = requireArguments().getLong("schedule_template_id")


        // Create ViewModel via Factory
        val viewModelFactory = CreateScheduleViewModelFactory(scheduleId)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateScheduleViewModel::class.java)


        // Back button click listener
        binding.createScheduleBackButton.setOnClickListener{
            view.findNavController().navigateUp()
        }

        // Time pickers
        binding.createScheduleWorkingTimeStart.setOnClickListener{
            TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                binding.createScheduleWorkingTimeStart.text = viewModel.getTime(hour, minute)
            }, 0,0,false).show()

        }

        binding.createScheduleWorkingTimeEnd.setOnClickListener{
            TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                binding.createScheduleWorkingTimeEnd.text = viewModel.getTime(hour, minute)
            }, 0,0,false).show()

        }

        binding.createScheduleBreakTimeStart.setOnClickListener{
            TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                binding.createScheduleBreakTimeStart.text = viewModel.getTime(hour, minute)
            }, 0,0,false).show()

        }

        binding.createScheduleBreakTimeEnd.setOnClickListener{
            TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                binding.createScheduleBreakTimeEnd.text = viewModel.getTime(hour, minute)
            }, 0,0,false).show()

        }

        // Enable/disable break time
        binding.createScheduleBreakTimeCheckbox.setOnCheckedChangeListener{view, isChecked ->
            binding.createScheduleBreakTimeStartText.isEnabled = isChecked
            binding.createScheduleBreakTimeStart.isEnabled = isChecked
            binding.createScheduleBreakTimeEndText.isEnabled = isChecked
            binding.createScheduleBreakTimeEnd.isEnabled = isChecked
        }


        // Create adapter for RecyclerView
        val adapter = DayOfWeekAdapter()
        adapter.submitListWithCheck(viewModel.daysOfWeek)
        binding.createScheduleDayOfWeekPicker.adapter = adapter


        // Done button click listener
        binding.createScheduleDoneButton.setOnClickListener{

            val workingTimeStart = binding.createScheduleWorkingTimeStart.text.toString()
            val workingTimeEnd = binding.createScheduleWorkingTimeEnd.text.toString()
            val haveBreak = binding.createScheduleBreakTimeCheckbox.isChecked
            val breakTimeStart = binding.createScheduleBreakTimeStart.text.toString()
            val breakTimeEnd = binding.createScheduleBreakTimeEnd.text.toString()
            val timeSector = binding.createScheduleTimeSector.text.toString()
            val workingDays = adapter.workingDaysOfWeek

            viewModel.updateSchedule(workingTimeStart, workingTimeEnd, haveBreak, breakTimeStart, breakTimeEnd, timeSector, workingDays)

            if (scheduleId != 0L){
                view.findNavController().navigateUp()
                Snackbar.make(view, getText(R.string.schedule_updated), Snackbar.LENGTH_SHORT).show()
            }else{
                requireNotNull(this.activity).finishAndRemoveTask()
            }
        }


        return view
    }

}
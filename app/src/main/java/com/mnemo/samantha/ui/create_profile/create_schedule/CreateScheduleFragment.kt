package com.mnemo.samantha.ui.create_profile.create_schedule

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
import com.mnemo.samantha.repository.Repository
import com.mnemo.samantha.repository.database.entity.ScheduleTemplate

class CreateScheduleFragment : Fragment() {

    private lateinit var binding: FragmentCreateScheduleBinding
    private lateinit var viewModel: CreateScheduleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_schedule, container, false)
        val view = binding.root

        // Get arguments
        val scheduleId = requireArguments().getLong("schedule_template_id")


        // Create ViewModel via Factory and bind it to View
        val application = requireNotNull(this.activity).application

        val repository = Repository.getInstance(application)

        val viewModelFactory = CreateScheduleViewModelFactory(scheduleId, repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateScheduleViewModel::class.java)


        // Back button click listener
        binding.createScheduleBackButton.setOnClickListener{
            view.findNavController().navigateUp()
        }

        // Done button click listener
        binding.createScheduleDoneButton.setOnClickListener{

            val workingTimeStart = binding.createScheduleWorkingTimeStart.text.toString()
            val workingTimeEnd = binding.createScheduleWorkingTimeEnd.text.toString()
            val breakTimeStart = binding.createScheduleBreakTimeStart.text.toString()
            val breakTimeEnd = binding.createScheduleBreakTimeEnd.text.toString()
            val timeSector = binding.createScheduleTimeSector.text.toString()

            viewModel.updateSchedule(workingTimeStart, workingTimeEnd, breakTimeStart, breakTimeEnd, timeSector)

            if (scheduleId != 0L){
                view.findNavController().navigateUp()
                Snackbar.make(view, getText(R.string.shedule_updated), Snackbar.LENGTH_SHORT).show()
            }else{
                requireNotNull(this.activity).finishAndRemoveTask()
            }
        }

        return view
    }

}
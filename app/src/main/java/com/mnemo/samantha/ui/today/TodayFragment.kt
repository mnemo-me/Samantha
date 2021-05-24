package com.mnemo.samantha.ui.today

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.mnemo.samantha.R
import com.mnemo.samantha.databinding.FragmentTodayBinding

class TodayFragment : Fragment() {

    private lateinit var binding: FragmentTodayBinding
    private lateinit var viewModel: TodayViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Bind View
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false)
        val view = binding.root
        val dividerItemDecoration = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
        binding.todaySchedule.addItemDecoration(dividerItemDecoration)

        binding.lifecycleOwner = this


        // Create ViewModel
        viewModel = ViewModelProvider(this).get(TodayViewModel::class.java)


        // Create adapter for RecycleView
        val adapter = AppointmentsAdapter()
        adapter.pictureFolder = viewModel.storagePath
        adapter.dateText = viewModel.dateText
        adapter.phoneCallClickListener = AppointmentsAdapter.PhoneCallClickListener { clientPhoneNumber ->
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$clientPhoneNumber" )
            startActivity(intent)
        }

        binding.todaySchedule.adapter = adapter

        viewModel.appointments.observe(viewLifecycleOwner, { todayClients ->
            adapter.addHeaderAndSubmitList(todayClients)
        })


        return view
    }

}
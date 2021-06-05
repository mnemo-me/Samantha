package com.mnemo.samantha.ui.monthly_schedule.day_schedule.add_client_dialog.choose_services

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.databinding.CheckboxServiceBinding
import com.mnemo.samantha.domain.entities.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChooseServicesAdapter : ListAdapter<Service, ChooseServicesAdapter.ViewHolder>(ServicesDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    private lateinit var checkList : SparseBooleanArray

    lateinit var checkServiceClickListener: CheckServiceClickListener

    fun submitListWithCheck(services: List<Service>?){
        adapterScope.launch {

            checkList = if (services != null) {
                SparseBooleanArray(services.size)
            }else{
                SparseBooleanArray()
            }

            withContext(Dispatchers.Main){
                submitList(services)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(position, getItem(position), checkList[position], checkServiceClickListener)
    }

    class ViewHolder private constructor(val binding: CheckboxServiceBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(position: Int, service: Service, isWasChecked: Boolean, checkServiceClickListener: CheckServiceClickListener){
            binding.service = service
            binding.checkboxService.isChecked = isWasChecked

            binding.checkboxService.setOnCheckedChangeListener{view, isChecked ->
                    checkServiceClickListener.onClick(position, service, isChecked)
            }
        }

        companion object{
            fun from(parent: ViewGroup) : ViewHolder{

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CheckboxServiceBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class ServicesDiffCallback : DiffUtil.ItemCallback<Service>(){
        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem == newItem
        }
    }


    // Click listeners
    class CheckServiceClickListener(val clickListener: (position: Int, service: Service, isChecked: Boolean) -> Unit){
        fun onClick(position: Int, service: Service, isChecked: Boolean) = clickListener(position, service, isChecked)
    }

    fun checkPosition(position: Int, isWasChecked: Boolean){
        checkList.append(position, isWasChecked)
    }
}
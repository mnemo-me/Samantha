package com.mnemo.samantha.ui.create_profile.services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.databinding.ServiceBinding
import com.mnemo.samantha.databinding.ServicesHeaderBinding
import com.mnemo.samantha.domain.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class ServicesAdapter : ListAdapter<ServicesAdapter.DataItem, RecyclerView.ViewHolder>(ServiceDiffCallback()){

    private var isHeaderAttached = false

    var addNewServiceClickListener: AddNewServiceClickListener = AddNewServiceClickListener {  }
    var serviceClickListener: ServiceClickListener = ServiceClickListener {  }

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun attachHeader(){
        isHeaderAttached = true
    }

    fun submitServicesList(services: List<Service>){
        adapterScope.launch {
            val items = if (isHeaderAttached){
                listOf(DataItem.Header) + services.map { DataItem.ServiceItem(it) }
            }else{
                services.map { DataItem.ServiceItem(it) }
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
            else -> throw ClassCastException("Unknown ViewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is HeaderHolder -> {
                holder.bind(addNewServiceClickListener)
            }
            is ViewHolder -> {
                val serviceItem = getItem(position) as DataItem.ServiceItem
                holder.bind(serviceItem.service, serviceClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHeaderAttached && position == 0){
            ITEM_VIEW_TYPE_HEADER
        } else{
            ITEM_VIEW_TYPE_ITEM
        }
    }


    // ViewHolder classes
    class ViewHolder private constructor(val binding: ServiceBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(service: Service, serviceClickListener: ServiceClickListener){

            binding.service = service
            binding.clickListener = serviceClickListener

            binding.servicePrice.text = service.price.toString()
        }

        companion object{
            fun from(parent: ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ServiceBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class HeaderHolder private constructor(val binding: ServicesHeaderBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(addNewServiceClickListener: AddNewServiceClickListener){
            binding.clickListener = addNewServiceClickListener
        }

        companion object{
            fun from(parent: ViewGroup) : HeaderHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ServicesHeaderBinding.inflate(layoutInflater, parent, false)

                return HeaderHolder(binding)
            }
        }
    }




    sealed class DataItem{

        abstract val id: Long

        data class ServiceItem(val service: Service) : DataItem(){
            override val id = service.id
        }

        object Header : DataItem(){
            override val id = Long.MIN_VALUE
        }
    }


    // DiffUtil callback
    class ServiceDiffCallback : DiffUtil.ItemCallback<DataItem>(){
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    // Click listeners
    class AddNewServiceClickListener(val clicklistener: () -> Unit){
        fun onClick() = clicklistener()
    }

    class ServiceClickListener(val clicklistener: (serviceId: Long) -> Unit){
        fun onClick(service: Service) = clicklistener(service.id)
    }
}
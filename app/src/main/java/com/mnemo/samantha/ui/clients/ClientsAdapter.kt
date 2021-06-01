package com.mnemo.samantha.ui.clients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.databinding.ClientsClientBinding
import com.mnemo.samantha.databinding.ClientsHeaderBinding
import com.mnemo.samantha.domain.Client
import com.mnemo.samantha.ui.loadImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class ClientsAdapter: ListAdapter<ClientsAdapter.DataItem, RecyclerView.ViewHolder>(ClientsDiffCallback()) {


    lateinit var addNewClientClickListener : AddNewClientClickListener
    lateinit var clickListener : ClientClickListener
    lateinit var pictureFolder: File


    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(clients: List<Client>?){
        adapterScope.launch {
            val items = when(clients){
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + clients.map{DataItem.ClientItem(it)}
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

        when(holder){
            is HeaderHolder -> {
                holder.bind(addNewClientClickListener)
            }

            is ViewHolder -> {
                val clientItem = getItem(position) as DataItem.ClientItem
                holder.bind(clientItem.client, clickListener, pictureFolder)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> ITEM_VIEW_TYPE_HEADER
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }


    class ViewHolder private constructor (val binding: ClientsClientBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(client: Client, clickListener: ClientClickListener, pictureFolder: File){

            binding.clientsClientAvatar.clipToOutline = true
            binding.client = client
            binding.clickListener = clickListener

            binding.clientsClientAvatar.loadImage(File(pictureFolder, "cl${client.id}.JPEG"))
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ClientsClientBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class HeaderHolder private constructor(val binding: ClientsHeaderBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: AddNewClientClickListener){

            binding.clickListener = clickListener
        }

        companion object{
            fun from(parent: ViewGroup): HeaderHolder{

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ClientsHeaderBinding.inflate(layoutInflater, parent, false)

                return HeaderHolder(binding)
            }
        }
    }



    sealed class DataItem {

        abstract val id: Long

        data class ClientItem(val client: Client) : DataItem() {
            override val id = client.id
        }

        object Header : DataItem() {
            override val id = Long.MIN_VALUE
        }
    }

    // DiffUtil callback
    class ClientsDiffCallback : DiffUtil.ItemCallback<DataItem>(){
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    // Click listeners
    class ClientClickListener(val clickListener: (clientId: Long) -> Unit){
        fun onClick(client: Client) = clickListener(client.id)
    }

    class AddNewClientClickListener(val clickListener: () -> Unit){
        fun onClick() = clickListener()
    }
}
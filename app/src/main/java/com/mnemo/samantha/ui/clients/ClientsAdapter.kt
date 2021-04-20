package com.mnemo.samantha.ui.clients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.R
import com.mnemo.samantha.data.Client
import com.mnemo.samantha.databinding.ClientsClientBinding
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class ClientsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var clients = mutableListOf<DataItem>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = clients.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER -> HeaderHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is ViewHolder -> {
                val clientItem = clients[position] as DataItem.ClientItem
                holder.bind(clientItem.client)
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

        fun bind(client: Client){

            binding.clientsClientAvatar.clipToOutline = true

            binding.clientsClientName.text = client.name
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ClientsClientBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class HeaderHolder private constructor(view: View): RecyclerView.ViewHolder(view){

        companion object{
            fun from(parent: ViewGroup): HeaderHolder{

                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.clients_header, parent, false)

                return HeaderHolder(view)
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
}
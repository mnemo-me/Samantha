package com.mnemo.samantha.ui.current_day

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.R
import com.mnemo.samantha.data.Client
import com.mnemo.samantha.databinding.TodayClientBinding
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class TodayClientsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var todaySchedule = linkedMapOf<Int, Client?>()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = todaySchedule.size

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
                val item = todaySchedule.entries.toTypedArray()[position]
                holder.bind(item)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> ITEM_VIEW_TYPE_HEADER
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolder private constructor(val binding: TodayClientBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MutableMap.MutableEntry<Int, Client?>) {
            val time = item.key
            binding.todayClientTime.text = time.toString() + ":00"

            val client = item.value

            if (client != null) {
                binding.todayClientName.text = client.name

                binding.todayClientAvatar.clipToOutline = true

                if (client.phone == null) {

                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TodayClientBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding);
            }
        }

    }

    class HeaderHolder private constructor(view: View): RecyclerView.ViewHolder(view){

        companion object{
            fun from(parent: ViewGroup): HeaderHolder{

                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.today_clients_header, parent, false)

                return HeaderHolder(view)
            }
        }
    }

}
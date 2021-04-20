package com.mnemo.samantha.ui.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.data.MonthlyStatistics
import com.mnemo.samantha.databinding.MonthlyStatisticsBinding
import com.mnemo.samantha.databinding.StatisticsHeaderBinding

private const val  ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class StatisticsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var statistics = mutableListOf<DataItem>()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = statistics.size

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> ITEM_VIEW_TYPE_HEADER
            else -> ITEM_VIEW_TYPE_ITEM
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
                val header = statistics[position] as DataItem.Header
                holder.bind(header.annualRevenue)
            }
            is ViewHolder -> {
                val monthlyStatisticsItem = statistics[position] as DataItem.MonthlyStatisticsItem
                holder.bind(monthlyStatisticsItem.monthlyStatistics)
            }
            else -> throw ClassCastException("Unknown viewHolder $holder")
        }
    }




    class ViewHolder private constructor(val binding: MonthlyStatisticsBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(monthlyStatistics: MonthlyStatistics){

            binding.monthlyStatisticsMonth.text = monthlyStatistics.month
            binding.monthlyStatisticsWorkingDays.text = monthlyStatistics.workingDays.toString()
            binding.monthlyStatisticsClients.text = monthlyStatistics.clients.toString()
            binding.monthlyStatisticsRevenue.text = monthlyStatistics.revenue.toString()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MonthlyStatisticsBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class HeaderHolder private constructor(val binding: StatisticsHeaderBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(annualRevenue: String){

            binding.statisticsHeaderAnnualRevenue.text = annualRevenue.toString()
        }

        companion object{
            fun from(parent: ViewGroup): HeaderHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StatisticsHeaderBinding.inflate(layoutInflater, parent, false)

                return HeaderHolder(binding)
            }
        }
    }




    sealed class DataItem {
        abstract val id: Long

        data class MonthlyStatisticsItem(val monthlyStatistics: MonthlyStatistics) : DataItem() {
            override val id = monthlyStatistics.id
        }

        class Header(val annualRevenue: String): DataItem(){
            override val id = Long.MIN_VALUE
        }
    }
}
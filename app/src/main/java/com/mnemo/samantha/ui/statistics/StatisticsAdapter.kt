package com.mnemo.samantha.ui.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnemo.samantha.databinding.MonthlyStatisticsBinding
import com.mnemo.samantha.databinding.StatisticsHeaderBinding
import com.mnemo.samantha.repository.data.Statistics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val  ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class StatisticsAdapter: ListAdapter<StatisticsAdapter.DataItem, RecyclerView.ViewHolder>(StatisticsDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(statistics: List<Statistics>){
        adapterScope.launch {
            val items = when (statistics){
                null -> listOf(DataItem.Header(100))
                else -> listOf(DataItem.Header(100)) + statistics.map { DataItem.StatisticsItem(it) }
            }
            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }

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
                val header = getItem(position) as DataItem.Header
                holder.bind(header.annualRevenue)
            }
            is ViewHolder -> {
                val statisticsItem = getItem(position) as DataItem.StatisticsItem
                holder.bind(statisticsItem.statistics)
            }
            else -> throw ClassCastException("Unknown viewHolder $holder")
        }
    }




    class ViewHolder private constructor(val binding: MonthlyStatisticsBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(statistics: Statistics){

            binding.monthlyStatisticsMonth.text = statistics.month.toString()
            binding.monthlyStatisticsWorkingDays.text = statistics.workingDays.toString()
            binding.monthlyStatisticsClients.text = statistics.clients.toString()
            binding.monthlyStatisticsRevenue.text = statistics.revenue.toString()
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

        fun bind(annualRevenue: Long){

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

        data class StatisticsItem(val statistics: Statistics) : DataItem() {
            override val id = (statistics.year * 100 + statistics.month).toLong()
        }

        class Header(val annualRevenue: Long): DataItem(){
            override val id = Long.MIN_VALUE
        }
    }

    // DiffUtil Callback
    class StatisticsDiffCallback : DiffUtil.ItemCallback<DataItem>(){
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}
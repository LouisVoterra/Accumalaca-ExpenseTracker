package com.accumalaca.expensestracking.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.accumalaca.expensestracking.databinding.ItemBudgetReportBinding
import com.accumalaca.expensestracking.model.BudgetReport
import java.text.NumberFormat

class BudgetReportAdapter(val reportList: ArrayList<BudgetReport>) :
    RecyclerView.Adapter<BudgetReportAdapter.BudgetReportViewHolder>() {

    class BudgetReportViewHolder(val binding: ItemBudgetReportBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetReportViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBudgetReportBinding.inflate(inflater, parent, false)
        return BudgetReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetReportViewHolder, position: Int) {
        val item = reportList[position]

        holder.binding.txtBudgetName.text = item.name
        holder.binding.txtUsedAmount.text = "IDR ${NumberFormat.getInstance().format(item.spent)}"
        holder.binding.txtMaxAmount.text = "IDR ${NumberFormat.getInstance().format(item.max)}"
        holder.binding.progressBar.max = item.max
        holder.binding.progressBar.progress = item.spent
        holder.binding.txtRemaining.text = "Budget left: IDR ${NumberFormat.getInstance().format(item.remaining)}"
    }


    override fun getItemCount(): Int = reportList.size

    fun updateList(newList: List<BudgetReport>) {
        reportList.clear()
        reportList.addAll(newList)
        notifyDataSetChanged()
    }
}

package com.accumalaca.expensestracking.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.accumalaca.expensestracking.databinding.BudgetItemLayoutBinding
import com.accumalaca.expensestracking.model.Budget

class BudgetListAdapter(val budgetList: ArrayList<Budget>)
    :RecyclerView.Adapter<BudgetListAdapter.BudgetViewHolder>(){
    class BudgetViewHolder(var binding: BudgetItemLayoutBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        var binding = BudgetItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BudgetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return budgetList.size
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.binding.txtNameTemp.text = budgetList[position].budgetName
        holder.binding.txtNominalTemp.text = budgetList[position].nominal.toString()
    }

    fun updateBudgetList(newBudgetList: List<Budget>){
        budgetList.clear()
        budgetList.addAll(newBudgetList)
        notifyDataSetChanged()
    }


}
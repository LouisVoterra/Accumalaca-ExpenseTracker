package com.accumalaca.expensestracking.view

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.accumalaca.expensestracking.R
import com.accumalaca.expensestracking.databinding.ItemExpenseBinding
import com.accumalaca.expensestracking.model.Expense
import com.google.android.material.chip.Chip
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExpenseListAdapter(private val expenseList: ArrayList<Expense>) :
    RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(var binding: ItemExpenseBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateExpenseList(newExpenseList: List<Expense>) {
        expenseList.clear()
        expenseList.addAll(newExpenseList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemExpenseBinding.inflate(inflater, parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun getItemCount() = expenseList.size

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]

        // Format tanggal dari Long ke String
        val outputFormat = SimpleDateFormat("dd MMM yyyy hh.mm a", Locale("id", "ID"))
        val formattedDate = outputFormat.format(Date(expense.date))

        holder.binding.txtDate.text = formattedDate
        holder.binding.chipCategory.text = expense.category
        holder.binding.txtAmount.text = "IDR ${NumberFormat.getInstance().format(expense.amount)}"

        holder.binding.txtAmount.setOnClickListener {
            val dialogView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.dialog_expense_detail, null)

            val dialog = AlertDialog.Builder(holder.itemView.context)
                .setView(dialogView)
                .create()

            val txtDate = dialogView.findViewById<TextView>(R.id.txtDate)
            val txtName = dialogView.findViewById<TextView>(R.id.txtName)
            val chipCategory = dialogView.findViewById<Chip>(R.id.chipCategory)
            val txtAmount = dialogView.findViewById<TextView>(R.id.txtAmount)
            val btnClose = dialogView.findViewById<Button>(R.id.btnClose)

            // Format tanggal
            val outputFormat = SimpleDateFormat("dd MMM yyyy hh.mm a", Locale("id", "ID"))
            val formattedDate = outputFormat.format(Date(expense.date))

            txtDate.text = formattedDate
            txtName.text = expense.expenseName
            chipCategory.text = expense.category
            txtAmount.text = "IDR ${NumberFormat.getInstance().format(expense.amount)}"

            btnClose.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }


}

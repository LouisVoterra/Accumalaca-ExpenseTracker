package com.accumalaca.expensestracking.view

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.accumalaca.expensestracking.databinding.FragmentCreateExpenseBinding
import com.accumalaca.expensestracking.model.Budget
import com.accumalaca.expensestracking.viewmodel.ExpenseViewModel
import com.accumalaca.expensestracking.viewmodel.ListBudgetViewModel
import com.accumalaca.expensestracking.util.SessionManager
import com.accumalaca.expensestracking.model.Expense
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CreateExpenseFragment : Fragment() {
    private lateinit var binding: FragmentCreateExpenseBinding
    private lateinit var viewModel: ListBudgetViewModel
    private var budgetList: List<Budget> = arrayListOf()
    private lateinit var expenseViewModel: ExpenseViewModel //variable expense model
    private var selectedBudget: Budget? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateExpenseBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this)[ListBudgetViewModel::class.java]
        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        // Tampilkan tanggal sekarang live
        val handler = android.os.Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val time = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID")).format(Date())
                binding.txtDate.text = time
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)

        // Refresh & observe budget list
        viewModel.refresh()
        expenseViewModel.refresh()

        viewModel.budgetLD.observe(viewLifecycleOwner) { budgets ->
            budgetList = budgets
            val budgetNames = budgets.map { it.budgetName }

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, budgetNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerBudget.adapter = adapter
        }

        // Saat item spinner dipilih, hitung progress berdasarkan total expense
        binding.spinnerBudget.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedBudget = budgetList[position]
                updateBudgetProgress()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        // Tambahkan data saat tombol ditekan
        binding.btnAddExpense.setOnClickListener {
            val expenseName = binding.editExpenseName.text.toString()
            val amountText = binding.editAmount.text.toString()
            val selected = selectedBudget

            if (expenseName.isEmpty() || amountText.isEmpty() || selected == null) {
                Toast.makeText(requireContext(), "Semua data harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toIntOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(requireContext(), "Nominal tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val username = SessionManager(requireContext()).getLoggedInUser() ?: ""
            val dateNow = System.currentTimeMillis()

            // Hitung total pengeluaran sebelumnya untuk budget ini
            val totalExpenseForBudget = expenseViewModel.expenseLD.value
                ?.filter { it.category == selected.budgetName && it.username == username }
                ?.sumOf { it.amount } ?: 0

            val sisaBudget = selected.nominal - totalExpenseForBudget
            if (amount > sisaBudget) {
                Toast.makeText(requireContext(), "Nominal melebihi sisa budget", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val expense = Expense(
                expenseName = expenseName,
                amount = amount,
                category = selected.budgetName,
                date = dateNow,
                username = username
            )

            expenseViewModel.addExpense(expense)
            Toast.makeText(requireContext(), "Expense berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Tombol back manual
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun updateBudgetProgress() {
        val selected = selectedBudget ?: return
        val username = SessionManager(requireContext()).getLoggedInUser() ?: ""

        val totalSpent = expenseViewModel.expenseLD.value
            ?.filter { it.category == selected.budgetName && it.username == username }
            ?.sumOf { it.amount } ?: 0

        val remaining = selected.nominal - totalSpent

        binding.txtSpentAmount.text = "IDR ${NumberFormat.getInstance().format(totalSpent)}"
        binding.txtTotalBudget.text = "IDR ${NumberFormat.getInstance().format(selected.nominal)}"

        binding.progressBudget.max = selected.nominal
        binding.progressBudget.progress = totalSpent
    }





}
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
import com.accumalaca.expensestracking.R
import com.accumalaca.expensestracking.databinding.FragmentCreateExpenseBinding
import com.accumalaca.expensestracking.model.Budget
import com.accumalaca.expensestracking.util.buildDB
import com.accumalaca.expensestracking.viewmodel.ListBudgetViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CreateExpenseFragment : Fragment() {
    private lateinit var binding: FragmentCreateExpenseBinding
    private lateinit var viewModel: ListBudgetViewModel
    private var budgetList: List<Budget> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateExpenseBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListBudgetViewModel::class.java)

        var handler = android.os.Handler(Looper.getMainLooper())
        var runnable = object : Runnable {
            override fun run() {
                val time = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id","ID")).format(Date())
                binding.txtDate.text = time
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)


        // Observe LiveData
        viewModel.budgetLD.observe(viewLifecycleOwner) { budgets ->
            budgetList = budgets

            val budgetNames = budgets.map { it.budgetName }

            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                budgetNames
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerBudget.adapter = adapter
        }

        // Jalankan refresh supaya data dimuat
        viewModel.refresh()

        // Optional: ketika user memilih budget
        binding.spinnerBudget.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                val selectedBudget = budgetList[position]
                Toast.makeText(requireContext(), "Dipilih: ${selectedBudget.budgetName}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


}
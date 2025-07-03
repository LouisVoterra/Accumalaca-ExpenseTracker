package com.accumalaca.expensestracking.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.accumalaca.expensestracking.databinding.FragmentReportListBinding
import com.accumalaca.expensestracking.util.SessionManager
import com.accumalaca.expensestracking.viewmodel.ReportViewModel
import java.text.NumberFormat


class ReportListFragment : Fragment()


{
    private lateinit var binding: FragmentReportListBinding
    private lateinit var viewModel: ReportViewModel
    private val adapter = BudgetReportAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        binding.recViewReport.layoutManager = LinearLayoutManager(requireContext())
        binding.recViewReport.adapter = adapter

        val username = SessionManager(requireContext()).getLoggedInUser() ?: ""

        viewModel.reportData.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

        viewModel.totalBudget.observe(viewLifecycleOwner) { totalBudget ->
            val totalSpent = viewModel.totalSpent.value ?: 0
            binding.txtTotalSummary.text = "IDR ${NumberFormat.getInstance().format(totalSpent)} / IDR ${NumberFormat.getInstance().format(totalBudget)}"
        }

        viewModel.totalSpent.observe(viewLifecycleOwner) { totalSpent ->
            val totalBudget = viewModel.totalBudget.value ?: 0
            binding.txtTotalSummary.text = "IDR ${NumberFormat.getInstance().format(totalSpent)} / IDR ${NumberFormat.getInstance().format(totalBudget)}"
        }

        viewModel.loadReport(username)
    }
}

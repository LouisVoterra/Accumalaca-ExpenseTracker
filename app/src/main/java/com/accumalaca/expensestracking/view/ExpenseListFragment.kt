package com.accumalaca.expensestracking.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.accumalaca.expensestracking.databinding.FragmentExpenseListBinding
import com.accumalaca.expensestracking.viewmodel.ExpenseViewModel

class ExpenseListFragment : Fragment() {
    private lateinit var binding: FragmentExpenseListBinding
    private lateinit var viewModel: ExpenseViewModel
    private val expenseListAdapter = ExpenseListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExpenseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        viewModel.refresh()

        binding.recViewExpense.layoutManager = LinearLayoutManager(context)
        binding.recViewExpense.adapter = expenseListAdapter

        binding.btnFabExpense.setOnClickListener {
            val action = ExpenseListFragmentDirections.actionCreateExpense()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.expenseLD.observe(viewLifecycleOwner, Observer {
            expenseListAdapter.updateExpenseList(it)

            if (it.isEmpty()) {
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.txtError.visibility = View.GONE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            binding.progressLoad.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.errorLD.observe(viewLifecycleOwner, Observer {
            binding.txtError.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    //Agar data baru langsung muncul setelah kembali dari CreateExpense
    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }
}

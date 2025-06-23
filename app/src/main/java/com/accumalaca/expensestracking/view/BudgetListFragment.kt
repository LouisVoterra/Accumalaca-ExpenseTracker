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
import com.accumalaca.expensestracking.R
import com.accumalaca.expensestracking.databinding.FragmentBudgetListBinding
import com.accumalaca.expensestracking.viewmodel.ListBudgetViewModel


class BudgetListFragment : Fragment() {
    private lateinit var binding:FragmentBudgetListBinding
    private lateinit var viewModel: ListBudgetViewModel
    private val budgetListAdapter = BudgetListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBudgetListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListBudgetViewModel::class.java)
        viewModel.refresh()
        binding.recViewBudget.layoutManager = LinearLayoutManager(context)
        binding.recViewBudget.adapter = budgetListAdapter

        binding.btnFab.setOnClickListener{
            val action = BudgetListFragmentDirections.actionCreateBudget()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    fun observeViewModel(){

        viewModel.budgetLD.observe(viewLifecycleOwner, Observer {
            budgetListAdapter.updateBudgetList(it)
            if(it.isEmpty()){
                binding.recViewBudget?.visibility = View.GONE
                binding.txtError.setText("Your budget still Empty.")
            }else{
                binding.recViewBudget?.visibility = View.VISIBLE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner,Observer{
            if(it == false){
                binding.progressLoad?.visibility = View.GONE
            }else{
                binding.progressLoad?.visibility = View.VISIBLE
            }
        })

        viewModel.budgetLoadErrorLD.observe(viewLifecycleOwner, Observer{
            if(it == false){
                binding.txtError?.visibility = View.GONE
            }else{
                binding.txtError?.visibility = View.VISIBLE
            }
        })
    }


}
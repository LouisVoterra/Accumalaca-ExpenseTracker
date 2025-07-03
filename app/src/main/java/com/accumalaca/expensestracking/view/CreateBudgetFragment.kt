package com.accumalaca.expensestracking.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.accumalaca.expensestracking.databinding.FragmentCreateBudgetBinding
import com.accumalaca.expensestracking.model.Budget
import com.accumalaca.expensestracking.util.SessionManager
import com.accumalaca.expensestracking.viewmodel.DetailBudgetViewModel


class CreateBudgetFragment : Fragment() {
    private lateinit var binding:FragmentCreateBudgetBinding
    private lateinit var viewModel: DetailBudgetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBudgetBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailBudgetViewModel::class.java)



        binding.btnAdd.setOnClickListener{

            val username = SessionManager(requireContext()).getLoggedInUser()//inisiasi

            val namaBudget = binding.txtNama.text.toString()
            val nominalStr = binding.txtNominal.text.toString()
            val nominal = nominalStr.toIntOrNull()

            if (username == null) {
                Toast.makeText(requireContext(), "User tidak ditemukan", Toast.LENGTH_SHORT).show() //pengecekan username
                return@setOnClickListener
            }

            if (namaBudget.isBlank()){
                Toast.makeText(requireContext(), "Silakan isi nama budget, jangan males", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (nominal == null){
                Toast.makeText(requireContext(), "Ga boleh males ngisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (nominal < 0){
                Toast.makeText(requireContext(), "ngutang kah anda kok sampe minus", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val budget = Budget(namaBudget, nominal, username) // tambah username

            val list = listOf(budget)
            viewModel.addBudget(budget) //diganti ya, karena nerimanya 1 pe4satu bukan list/batch
            Toast.makeText(view.context, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show()

            // Kembali ke fragment sebelumnya
            Navigation.findNavController(requireView()).popBackStack()
        }

        // Tombol back manual
        binding.btnBack?.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

        // Tombol back bawaan HP
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            Navigation.findNavController(requireView()).popBackStack()
        }
    }


}
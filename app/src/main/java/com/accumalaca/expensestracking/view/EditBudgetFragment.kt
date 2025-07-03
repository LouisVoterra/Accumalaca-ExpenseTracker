package com.accumalaca.expensestracking.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.accumalaca.expensestracking.R
import com.accumalaca.expensestracking.databinding.FragmentEditBudgetBinding
import com.accumalaca.expensestracking.viewmodel.DetailBudgetViewModel


class EditBudgetFragment : Fragment() {
    private lateinit var binding : FragmentEditBudgetBinding
    private lateinit var viewModel: DetailBudgetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBudgetBinding.inflate(inflater,container,false)


        return binding.root
    }

    fun observeViewModel() {
        viewModel.budgetLD.observe(viewLifecycleOwner, Observer {
            binding.txtNama.setText(it.budgetName)
            binding.txtNominal.setText(it.nominal.toString())
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //inisialisasi viewModel

        viewModel = ViewModelProvider(this).get(DetailBudgetViewModel::class.java)

       // binding.textView.text = "Edit Budget"  //buat ngubah value dari textView sebelumnya dari sisi UI
       // binding.btnAdd.text = "Save Changes" //buat ngubah value dari textView sebelumnya dari sisi UI

        binding.textView.setText(R.string.edit_budget)
        binding.btnAdd.setText(R.string.save_changes)
        //g beda jauh, konsitesi doang

        //Mengambil argumen(data) yang dikirim ke fragment dengan cara yang aman
        val uuid = EditBudgetFragmentArgs.fromBundle(requireArguments()).uuid


        viewModel.fetch(uuid) //manggil method fetch dari viewModel

        observeViewModel()



        binding.btnAdd.setOnClickListener {

            val namaBudget = binding.txtNama.text.toString()
            //btw toInt() ni agak galak cuy, kalau null langsung di force close
            val nominalStr = binding.txtNominal.text.toString()
            val nominal = nominalStr.toIntOrNull() //nah ini kalo misal null ga sampe di force close


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

            viewModel.updateBudget(namaBudget, nominal, uuid)

            Toast.makeText(view.context,"Berhasil update budget", Toast.LENGTH_SHORT).show()


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
package com.accumalaca.expensestracking.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.accumalaca.expensestracking.R
import com.accumalaca.expensestracking.databinding.FragmentReportListBinding


class ReportListFragment : Fragment() {
    private lateinit var binding: FragmentReportListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReportListBinding.inflate(inflater,container,false)
        return binding.root
    }


}
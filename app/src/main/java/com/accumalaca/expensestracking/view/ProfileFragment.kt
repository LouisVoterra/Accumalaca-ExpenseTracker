package com.accumalaca.expensestracking.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.accumalaca.expensestracking.R
import com.accumalaca.expensestracking.databinding.FragmentProfileBinding
import com.accumalaca.expensestracking.util.SessionManager
import com.accumalaca.expensestracking.viewmodel.AuthViewModel


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var session: SessionManager //kita perlu panggil sessionManager dulu
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        //bikin object dari class SessionManager, tapi parameter bukan lagi pake this, karena ini fragment, jadi
        //bisa pakai requireContext
        session = SessionManager(requireContext())


        //cuma buat biar tau ini akun siapa si, biar gampang testingya
        val username = session.getLoggedInUser()
        binding.txtWelcomeUser.text = if (username != null) {
            "Selamat datang, @$username"
        } else {
            "Selamat datang!"
        }


        binding.btnSignOut.setOnClickListener{
            session.clearSession()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish() //yang ini harus requireActivity biar bisa panggil finish()
        }

        binding.btnSave.setOnClickListener {
            val old_password = binding.txtOldPassword.text.toString()
            val new_password = binding.txtNewPassword.text.toString()
            val repeat_password = binding.txtRepeatPassword.text.toString()

            if (old_password.isBlank()){
                Toast.makeText(requireContext(), "Silahkan mengisi kembali Old Password Anda", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (new_password.isBlank()){
                Toast.makeText(requireContext(), "Silahkan mengisi kembali New Password Anda", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (repeat_password.isBlank()){
                Toast.makeText(requireContext(), "Silahkan mengisi kembali Repeat Password Anda", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (new_password != repeat_password){
                Toast.makeText(requireContext(), "Silahkan cocokkan kembali password Anda", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val username = session.getLoggedInUser()

            if (username == null) {
                Toast.makeText(requireContext(), "ngetest", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.updateUser(username,old_password,new_password) { success, message ->
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }


}
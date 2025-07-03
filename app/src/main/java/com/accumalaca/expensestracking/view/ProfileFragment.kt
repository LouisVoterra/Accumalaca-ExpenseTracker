package com.accumalaca.expensestracking.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.accumalaca.expensestracking.R
import com.accumalaca.expensestracking.databinding.FragmentProfileBinding
import com.accumalaca.expensestracking.model.User
import com.accumalaca.expensestracking.model.UserBinding
import com.accumalaca.expensestracking.util.SessionManager
import com.accumalaca.expensestracking.viewmodel.AuthViewModel


class ProfileFragment : Fragment(), ProfileHandler {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var session: SessionManager
    private lateinit var viewModel: AuthViewModel
    private val user = UserBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.user = user
        binding.handler = this

        session = SessionManager(requireContext())
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        val username = session.getLoggedInUser() ?: ""
        user.username.set(username)

        return binding.root
    }

    override fun onSignOutClick() {
        session.clearSession()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }

    override fun onSaveClick() {
        val old = user.oldPassword.get() ?: ""
        val new = user.newPassword.get() ?: ""
        val repeat = user.repeatPassword.get() ?: ""
        val username = user.username.get() ?: ""

        when {
            old.isBlank() || new.isBlank() || repeat.isBlank() -> {
                toast("Semua field harus diisi")
                return
            }
            new != repeat -> {
                toast("Password tidak cocok")
                return
            }
        }

        viewModel.updateUser(username, old, new) { success, message ->
            requireActivity().runOnUiThread {
                toast(message)
            }
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}

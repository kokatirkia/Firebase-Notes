package com.kt.notes.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kt.notes.R
import com.kt.notes.databinding.SignUpFragmentBinding
import com.kt.notes.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: SignUpFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignUpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        setUpOnClickListener()
        setUpObservers()
    }

    private fun setUpObservers() {
        signUpViewModel.isRegistrationSuccess().observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(activity, "Registration Successful", Toast.LENGTH_SHORT).show()
                attachMainFragment()
            }
        })

        signUpViewModel.registrationResponseMessage().observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.INVISIBLE
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun attachMainFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, MainFragment())
            ?.commit()
    }

    private fun setUpOnClickListener() {
        binding.signUpButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            signUpViewModel.createUser(
                binding.editTextTextUserName.text.toString(),
                binding.editTextPassword.text.toString(),
                binding.editTextRepeatedPassword.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.kt.notes.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.kt.notes.R
import com.kt.notes.databinding.LoginFragmentBinding
import com.kt.notes.main.MainFragment
import com.kt.notes.signup.SignUpFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setUpOnClickListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        loginViewModel.isSignInSuccessful().observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it) {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(activity, "login Successful", Toast.LENGTH_SHORT).show()
                    attachMainFragment()
                }
            }
        })

        loginViewModel.loginResponseMessage().observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUpOnClickListeners() {
        binding.loginButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            loginViewModel.signIn(
                binding.editTextEmailAddress.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }

        binding.signUp.setOnClickListener { attachRegistrationFragment() }
    }

    private fun attachMainFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, MainFragment())
            ?.commit()
    }

    private fun attachRegistrationFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, SignUpFragment())
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
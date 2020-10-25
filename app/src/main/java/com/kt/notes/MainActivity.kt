package com.kt.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kt.notes.databinding.ActivityMainBinding
import com.kt.notes.login.LoginFragment
import com.kt.notes.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Notes);
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        if (savedInstanceState == null) {
            if (sharedViewModel.isUserSignedIn()) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MainFragment())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment())
                    .commit()
            }
        }
    }
}
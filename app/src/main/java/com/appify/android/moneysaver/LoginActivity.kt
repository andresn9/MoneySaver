package com.appify.android.moneysaver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.appify.android.moneysaver.databinding.ActivityLoginBinding
import com.appify.android.moneysaver.databinding.ActivityLoginBinding.inflate
import com.appify.android.moneysaver.databinding.ActivityMainBinding
import com.appify.android.moneysaver.databinding.ActivityMainBinding.inflate

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root

        binding.buttonLoginEmail.setOnClickListener {
            switchActivities(view)
        }

        setContentView(view)
    }

    fun switchActivities(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
}
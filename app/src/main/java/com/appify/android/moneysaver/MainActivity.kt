package com.appify.android.moneysaver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.appify.android.moneysaver.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //Declare binding variable corresponding to activity_main.xml The name of the binding class is generated by converting the name of the XML file to Pascal case and adding the word "Binding" to the end. In this case activity_main.xml --> ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)//setup fragment with navigation bar
        val navController  = findNavController(R.id.fragment)//fragment that contains layouts, chronology,plannning, report etc. These fragments are constraint layouts that work like activities


        bottomNavigationBar.setupWithNavController(navController)





    }
}
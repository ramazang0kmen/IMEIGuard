package com.lideatech.imeiguard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.firebase.auth.FirebaseAuth
import com.lideatech.imeiguard.databinding.ActivityStartedBinding

class StartedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartedBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        supportActionBar?.hide()

        val currentUser = auth.currentUser
        if (currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}
package com.lideatech.imeiguard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.lideatech.imeiguard.databinding.ActivityStartedBinding

class StartedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(RegisterFragment())

        supportActionBar?.hide()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frame_layout, fragment)
        }
    }


}
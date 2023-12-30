package com.lideatech.imeiguard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.lideatech.imeiguard.databinding.FragmentGetStartedBinding

class GetStartedFragment : Fragment() {
    private lateinit var binding: FragmentGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentGetStartedBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_started, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getStartedButton.setOnClickListener { GetStarted() }
    }

    private fun GetStarted() {
        val action = GetStartedFragmentDirections.actionGetStartedFragmentToLoginFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }
}
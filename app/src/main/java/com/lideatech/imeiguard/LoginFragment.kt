package com.lideatech.imeiguard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.lideatech.imeiguard.databinding.FragmentGetStartedBinding
import com.lideatech.imeiguard.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.singUpButton.setOnClickListener { register() }
    }

    private fun login() {
        val email = binding.TextEmailAddress.text.toString()
        val password = binding.TextPassword.text.toString()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(context?.applicationContext,"E-posta ve şifrenizi kontrol ediniz.", Toast.LENGTH_LONG).show()
        }
    }

    private fun register() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }
}
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
import com.lideatech.imeiguard.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.singUpButton.setOnClickListener { register() }
        binding.loginButton.setOnClickListener { login() }
    }

    private fun login() {
        binding.loginButton.isEnabled = false

        val email = binding.TextEmailAddress.text.toString()
        val password = binding.TextPassword.text.toString()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful) {


                val intent = Intent(view?.context, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(context?.applicationContext,"E-posta ve şifrenizi kontrol ediniz.", Toast.LENGTH_LONG).show()
        }
    }

    private fun register() {
        binding.singUpButton.isEnabled = false

        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }
}
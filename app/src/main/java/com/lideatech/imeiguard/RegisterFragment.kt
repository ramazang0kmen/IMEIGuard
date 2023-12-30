package com.lideatech.imeiguard

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.lideatech.imeiguard.databinding.FragmentRegisterBinding

class RegisterFragment() : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
//        binding = FragmentRegisterBinding.inflate(layoutInflater)
//        val view = binding.root
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)


        Toast.makeText(this.context, binding.registerButton.toString(), Toast.LENGTH_LONG).show()

        binding.registerButton.setOnClickListener(this)
        binding.TextEmailAddress.setOnClickListener(this)

        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fragment üzerindeki view elemanlarına erişim veya diğer işlemler burada yapılır

        Toast.makeText(context?.applicationContext,binding.registerButton.text, Toast.LENGTH_LONG).show()
    }

    private fun register() {
        val email = binding.TextEmailAddress.text.toString()
        val password = binding.textPassword.text.toString()
        //val namesurname = binding.textNameSurname.text.toString()
        //val date = binding.textDate.text.toString()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            //asenkron
            if (task.isSuccessful) {
                //diğer aktivite
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }else{
                // Kayıt başarısız olduğunda
                Log.e("RegisterFragment", "Kayıt başarısız", task.exception)
                Toast.makeText(context?.applicationContext, "Kayıt başarısız: ${task.exception?.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener { exception ->
            Log.e("RegisterFragment", "Kayıt hatası", exception)
            Toast.makeText(context?.applicationContext, "Bilgilerinizi kontrol ediniz.",Toast.LENGTH_LONG).show()
        }
    }

    private fun login() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun onClick(view: View?) {
        if(view?.id == R.id.TextEmailAddress){
            Toast.makeText(context?.applicationContext,"butona tıklandı", Toast.LENGTH_LONG).show()
        }
    }
}
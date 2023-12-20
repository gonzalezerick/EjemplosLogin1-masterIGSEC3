package com.example.ejemploslogin1_master

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ejemploslogin1_master.databinding.ActivityRegistrarBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class registrarActivity : AppCompatActivity() {

    //Activar viewBinding
    private lateinit var binding : ActivityRegistrarBinding

    //Auth firebase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar auth
        auth = Firebase.auth

        //Reconocer cuando le demos click a registrar
        binding.btnRegistrar.setOnClickListener {
            //Obtener los datos para el registros

            val correo: String = binding.etEmail.text.toString()
            val pass1: String = binding.etPassword.text.toString()
            val pass2: String = binding.etPassword2.text.toString()

            //Validar que los datos no sean vacios
            if (correo.isEmpty()){
                binding.etEmail.error = "Ingrese un correo"
                return@setOnClickListener
            }
            if (pass1.isEmpty()){
                binding.etPassword.error = "Ingrese una contraseña"
            }
            if (pass2.isEmpty()){
                binding.etPassword2.error = "Ingrese confirmación de contraseña"
            }

            if(pass1 == pass2){
                registrarUsuario(correo, pass1)
            }

        }

    }

    private fun registrarUsuario(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this, "El usuario se ha creado correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
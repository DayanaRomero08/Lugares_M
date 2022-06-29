package com.lugares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.google.firebase.ktx.Firebase
import com.lugares.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    //Con esta variable accedemos a los elementos de la parte visual de la programacion
    private lateinit var  binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

     // Se define el metodo para el login
      binding.btLogin.setOnClickListener{
          haceLoging();
      }
        // Se define el metodo para el registro
        binding.btRegister.setOnClickListener{
            haceRegister();
        }

    }

    private fun haceLoging() {
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()
        // Se hace el login
        auth.signInWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    Log.d("Autenticando ","Autenticado")
                    val user = auth.currentUser // Para recuperar el usuario
                    actualiza(user)

                } else {
                    Log.d("Autenticando ","Fallo")
                    Toast.makeText(baseContext,"Fallo", Toast.LENGTH_LONG).show()
                    actualiza(null)
                }
    }


    private fun haceRegister() {
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()
        // Se hace el registro
        auth.createUserWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this){ task ->
              if(task.isSuccessful){
                Log.d("Creando usuario ","Registrado")
                val user = auth.currentUser // Para recuperar el usuario
                actualiza(user)

            } else {
            Log.d("Creando usuario ","Fallo")
            Toast.makeText(baseContext,"Fallo", Toast.LENGTH_LONG).show()
            actualiza(null)
        }
    }
}

    private fun actualiza(user: FirebaseUser?) {
       if(user != null){
           val intent = Intent(this,Principal::class.java)
        startActivity(intent)
       }
    }//Esto hara que una vez autenticado... no pida mas o menos que se cierre la sesion
        public  override fun  onStart(){
            super.onStart()
            val usuario=auth.currentUser
            actualiza(usuario)
        }        }

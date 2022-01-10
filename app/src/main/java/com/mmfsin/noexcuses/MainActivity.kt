package com.mmfsin.noexcuses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        read()
    }

    private fun read(){
        Firebase.database.reference.child("hola").get()
            .addOnSuccessListener {
                texto.text = it.value.toString()

            }.addOnFailureListener {
                texto.text = it.localizedMessage
            }
    }
}
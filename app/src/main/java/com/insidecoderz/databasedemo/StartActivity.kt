package com.insidecoderz.databasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class StartActivity : AppCompatActivity() {

    lateinit var sign_in_start:TextView
    lateinit var sign_up_start:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        sign_up_start=findViewById(R.id.sign_up_start)
        sign_in_start=findViewById(R.id.sign_in_start)


        sign_in_start.setOnClickListener {
            val intent  = Intent(applicationContext,SignIn::class.java)
            startActivity(intent)
        }

        sign_up_start.setOnClickListener {
            val intent  = Intent(applicationContext,SignUp::class.java)
            startActivity(intent)
        }



    }
}
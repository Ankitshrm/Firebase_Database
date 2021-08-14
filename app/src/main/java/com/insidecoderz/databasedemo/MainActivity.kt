package com.insidecoderz.databasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser
    var mAuth : FirebaseAuth?=null
    lateinit var signout:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signout=findViewById(R.id.signout)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        mAuth= FirebaseAuth.getInstance()

        signout.setOnClickListener {
            mAuth!!.signOut()
            val intent = Intent(applicationContext, StartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)


        }
    }
}
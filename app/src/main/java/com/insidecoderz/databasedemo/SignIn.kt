package com.insidecoderz.databasedemo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class SignIn : AppCompatActivity() {

    lateinit var signin_2_signup:LinearLayout
    lateinit var btn_signin:TextView
    lateinit var email_signin:EditText
    lateinit var password_signin:EditText
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signin_2_signup = findViewById(R.id.signin_2_signup)
        btn_signin = findViewById(R.id.btn_signin)
        email_signin = findViewById(R.id.email_signin)
        password_signin = findViewById(R.id.password_signin)



        signin_2_signup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        mAuth= FirebaseAuth.getInstance()

        btn_signin.setOnClickListener {
            LoginUser()
        }

    }

    private fun LoginUser() {

        val email:String =email_signin.text.toString()
        val password:String =password_signin.text.toString()

        if (email==""||password=="") {

            Toast.makeText(this,"All fields are required", Toast.LENGTH_SHORT).show()

        }
        else
        {
            mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {task->
                    if (task.isSuccessful)
                    {
                        val intent =Intent(this,MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)

                    }
                    else{
                        Toast.makeText(this,"Error Message: "+task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

}






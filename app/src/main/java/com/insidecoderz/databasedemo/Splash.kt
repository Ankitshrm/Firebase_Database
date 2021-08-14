package com.insidecoderz.databasedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(FirebaseAuth.getInstance().currentUser == null) {

            val background = object : Thread() {
                override fun run() {
                    try {
                        sleep(3000)
                        val intent = Intent(applicationContext, StartActivity::class.java)
                        startActivity(intent)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            background.start()
        }
        else
        {
            val backgroundonstart =object :Thread(){
                override fun run() {
                    try{
                        sleep(1000)
                        val intent = Intent(baseContext, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                }
            }
            backgroundonstart.start()

        }


    }
}
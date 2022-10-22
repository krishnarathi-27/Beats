package com.music.beats

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.music.beats.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_temp.*

class TempActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        userID.text = "User ID :: $userId"
        emailID.text = "Email ID :: $emailId"



        logOut.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@TempActivity,LoginActivity::class.java))

            finish()
        }
    }

//    fun nextPage(view : View){
//
//        val intent = Intent(this@TempActivity,MainActivity2::class.java)
//        startActivity(intent)
//    }
}
package com.music.beats

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.music.beats.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.email
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN: Int = 123
    private val TAG = "SignInActivity Tag"
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        var token = getSharedPreferences("emailAdd", Context.MODE_PRIVATE)
//
//        if(token.getString("loginusername"," ")!= " ")
//        {
//            val intent = Intent(this,MainActivity2::class.java)
//            startActivity(intent)
//            finish()
//        }

        //login using google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        signInButton1.setOnClickListener {
            signIn()
        }

        //login using email
        loginButton.setOnClickListener {
            when{
                TextUtils.isEmpty(email.text.toString().trim{ it <= ' '})->{
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(password.text.toString().trim{ it <= ' '})->{
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val emailAdd:String = email.text.toString().trim{ it <= ' '}
                    val passWord:String = password.text.toString().trim{ it <= ' '}

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(emailAdd,passWord)
                        .addOnCompleteListener{ task ->

                                //If registration is successfully done
                                if(task.isSuccessful){

                                    //Firebase registered user
                                    val firebaseUser : String = FirebaseAuth.getInstance().currentUser!!.uid

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "You are logged in successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(this@LoginActivity,MainActivity2::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id",firebaseUser)
                                    intent.putExtra("email_id",emailAdd)
                                    startActivity(intent)

//                                    var editor = token.edit()
//                                    editor.putString("loginusername",emailAdd)
//                                    editor.commit()


                                }
                                else{
                                    //If registration is not successfull
                                    Toast.makeText(
                                        this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                }
            }
        }
    }

    fun registerHere(view :View){

        val intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        signInButton1.visibility = View.GONE
        progressBar1.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {
            val auth = auth.signInWithCredential(credential).await()
            val firebaseUser = auth.user
            withContext(Dispatchers.Main) {
                updateUI(firebaseUser)
            }
        }

    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser != null) {

            val mainActivity2Intent = Intent(this, MainActivity2::class.java)
            startActivity(mainActivity2Intent)
            finish()
        } else {
            signInButton1.visibility = View.VISIBLE
            progressBar1.visibility = View.GONE
        }
    }

}
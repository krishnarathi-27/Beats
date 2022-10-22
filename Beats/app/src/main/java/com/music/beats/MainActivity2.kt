package com.music.beats

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.music.beats.databinding.ActivityMain2Binding
import java.util.*
import kotlin.collections.ArrayList


class MainActivity2 : AppCompatActivity() {

     private lateinit var binding : ActivityMain2Binding
     private lateinit var musicAdapter: MusicAdapter
     val musicList = ArrayList<String>()
     val displayList = ArrayList<String>()
     private lateinit var auth: FirebaseAuth

       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestRuntimePermission()

           binding = ActivityMain2Binding.inflate(layoutInflater)
           //setContentView(R.layout.activity_main)
           setContentView(binding.root)



        //for music RECYCLE VIEW

           musicList.add("Butter")
           musicList.add("Permission to Dance")
           musicList.add("Life Goes On")
           musicList.add("Fake Love")
           musicList.add("Yet to Come")
           musicList.add("Dynamite")
           musicList.add("Spring Day")
           musicList.add("MIC Drop")
           musicList.add("RUN")
           musicList.add("Blood Sweat & Tears")
           musicList.add("Black Swan")
           musicList.add("Boy With Luv")
           musicList.add("Bad Decisions")
           musicList.add("Silver Spoon")
           musicList.add("Save ME")
           musicList.add("Friends")
           displayList.addAll(musicList)


           binding.musicRV.setHasFixedSize(true)
           binding.musicRV.setItemViewCacheSize(13)
           binding.musicRV.layoutManager = LinearLayoutManager(this@MainActivity2)
           musicAdapter = MusicAdapter(this@MainActivity2, displayList)
           binding.musicRV.adapter = musicAdapter



// logout code
//        loginB.setOnClickListener {
//            var token = getSharedPreferences("emailAdd", Context.MODE_PRIVATE)
//            loginB.text = token.getString("loginusername"," ")
//            var editor = token.edit()
//            editor.putString("loginusername"," ")
//            editor.commit()
//            val intent = Intent(this,LoginActivity::class.java)
//            startActivity(intent)
//        }

            auth = FirebaseAuth.getInstance()

           findViewById<Button>(R.id.signOutButton).setOnClickListener {
               auth.signOut()

               startActivity(Intent(this,MainActivity::class.java))
           }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item,menu)
       val menuItem = menu!!.findItem(R.id.search_action)

        if(menuItem != null)
        {
            val searchView = menuItem.actionView as SearchView
            searchView.maxWidth

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    displayList.clear()
                    if(newText!!.isNotEmpty()) {
                        displayList.clear()
                        val search = newText.toLowerCase(Locale.getDefault())

                        for (music in musicList) {
                            if(music.toLowerCase(Locale.getDefault()).contains(search)){
                                displayList.add(music)
                            }
                            binding.musicRV.adapter!!.notifyDataSetChanged()
                        }
                    }   else{
                        displayList.clear()
                        displayList.addAll(musicList)
                    }
                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
      }


    fun startLoginActivity(view : View){

        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

    //for requesting permission
    private fun requestRuntimePermission(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),13)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 13){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show()
            else
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),13)
        }
    }

    

}


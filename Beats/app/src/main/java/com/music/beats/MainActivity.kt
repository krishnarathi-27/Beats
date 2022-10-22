package com.music.beats

import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.music.beats.databinding.ActivityMainBinding

import java.util.*

import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setTheme(R.style.Theme_Beats)
       binding = ActivityMainBinding.inflate(layoutInflater)
       //   setContentView(R.layout.activity_main)
        setContentView(binding.root)
           startSecondActivity()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun startSecondActivity(){

        if(!isDestroyed){

            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            var mediaPlayer = MediaPlayer.create(this,R.raw.audio)
            mediaPlayer.start()
            
            val tmtask = timerTask{

                
                if(!isDestroyed){
                    startActivity(intent)
                    mediaPlayer?.release()
                    mediaPlayer = null
                    finish()
                }
            }

            val timer = Timer()
            timer.schedule(tmtask,2000)
//            mediaPlayer.stop()
        }
    }

}
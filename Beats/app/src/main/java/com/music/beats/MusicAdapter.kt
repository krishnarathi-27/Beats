package com.music.beats

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.music.beats.databinding.MusicViewBinding

class MusicAdapter(private val context: Context, private val musicList: ArrayList<String>) : RecyclerView.Adapter<MusicAdapter.MyHolder>() {
    class MyHolder(binding: MusicViewBinding) : RecyclerView.ViewHolder(binding.root){
        val titlee = binding.songName

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MyHolder {
        return MyHolder(MusicViewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    //this is called when music view is going to display on the screen
    override fun onBindViewHolder(holder: MusicAdapter.MyHolder, position: Int) {
        holder.titlee.text = musicList[position]
    }

    override fun getItemCount(): Int {
        return musicList.size
    }
}
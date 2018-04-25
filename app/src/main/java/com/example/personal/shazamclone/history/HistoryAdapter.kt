package com.example.personal.shazamclone.history

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.personal.shazamclone.R.layout.song_item
import com.example.personal.shazamclone.data.identify.db.room.SongEntity
import com.example.personal.shazamclone.utils.ctx
import kotlinx.android.synthetic.main.song_item.view.*

/**
 * Created by personal on 4/23/2018.
 */
class HistoryAdapter(private var songs: MutableList<SongEntity>,
                     private val itemClick: (SongEntity) -> Unit) :
        RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{

        val view = LayoutInflater.from(parent.ctx).inflate(song_item,parent,false)

        return ViewHolder(view,itemClick)
    }

    override fun getItemCount(): Int = songs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindSong(songs[position])
    }

    class ViewHolder(view: View, private val itemClick: (SongEntity) -> Unit)
        : RecyclerView.ViewHolder(view) {

        fun bindSong(song : SongEntity){

            with(song){

                itemView.song_title.text =  name
                itemView.song_artist.text = artist
                itemView.song_album.text = album

                itemView.setOnClickListener{ itemClick(this)}
            }
        }
    }

    fun updateDataset(songsList: List<SongEntity>){

        songs = songsList.toMutableList()
        notifyDataSetChanged()
    }

}
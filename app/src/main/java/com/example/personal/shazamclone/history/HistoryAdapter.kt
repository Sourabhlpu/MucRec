package com.example.personal.shazamclone.history

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.R.layout.song_item
import com.example.personal.shazamclone.data.identify.db.room.SongEntity
import com.example.personal.shazamclone.utils.ctx
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.song_item.view.*

/**
 * Created by personal on 4/23/2018.
 * This class defines the adapter for the historyFragment
 */

/*
 * the class's constructor accepts the mutable list of songs
 * and a lambda which takes a songEntity object and returns Unit object
 */
class HistoryAdapter(private var songs: MutableList<SongEntity>,
                     private val itemClick: (SongEntity) -> Unit) :
        RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {



    // here we inflate the layout for the list items and the create ViewHolder object
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{

        val view = LayoutInflater.from(parent.ctx).inflate(song_item,parent,false)

        // return the ViewHolder object passing in the list item view and the lambda
        return ViewHolder(view,itemClick)
    }

    override fun getItemCount(): Int = songs.size // returns the count of data items

    // binds the data to the views in the list item layout
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindSong(songs[position])
    }

    // and inner view holder class that will hold the reference to the views in the list item layout
    class ViewHolder(view: View, private val itemClick: (SongEntity) -> Unit)
        : RecyclerView.ViewHolder(view) {

        fun bindSong(song : SongEntity){

            with(song){

                itemView.song_title.text =  name
                itemView.song_artist.text = artist
                itemView.song_album.text = album

                if(imageUrl.isNullOrEmpty())
                {
                    Picasso.get().load(R.drawable.album_art_placeholder)
                }
                else {

                    Picasso.get().load(imageUrl!!)
                            .error(R.drawable.album_art_placeholder)
                            .placeholder(R.drawable.album_art_placeholder)
                            .into(itemView.album_art_image)
                }

                itemView.setOnClickListener{ itemClick(this)}
            }
        }
    }

    // method to update the data set
    fun updateDataset(songsList: List<SongEntity>){

        songs = songsList.toMutableList()
        notifyDataSetChanged()
    }

}
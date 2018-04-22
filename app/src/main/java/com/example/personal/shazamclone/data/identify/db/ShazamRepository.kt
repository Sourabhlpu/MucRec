package com.example.personal.shazamclone.data.identify.db

import com.example.personal.shazamclone.data.identify.db.room.SongDatabase
import com.example.personal.shazamclone.domain.Song

/**
 * Created by personal on 4/22/2018.
 */

class ShazamRepository{

    companion object {

        val instance by lazy { ShazamRepository() }
    }

    fun saveSong(song : Song) = SongDatabase.instance.songDao().insert(song)

    fun getAllSongs() : List<Song> = SongDatabase.instance.songDao().getAll()

}
package com.example.personal.shazamclone.data.identify.db

import com.example.personal.shazamclone.AppExecutors
import com.example.personal.shazamclone.data.identify.db.room.SongEntity
import com.example.personal.shazamclone.data.identify.network.NetworkDataSource
import com.example.personal.shazamclone.utils.App

/**
 * Created by personal on 4/22/2018.
 */

class ShazamRepository{

    companion object {

        val instance by lazy { ShazamRepository() }
    }

    fun saveSong(song : SongEntity) {

        AppExecutors.instance.diskIO.execute {

            App.database.songDao().insert(song)
        }

    }

    fun getAllSongs() : List<SongEntity> = App.database.songDao().getAll()

    fun updateImageUrl(url : String, ylink : String)
            = App.database.songDao().updateImageLink(url,ylink)

    fun getImageUrl(isrc : String, track: String, artist: String) : String =
            NetworkDataSource.instance.getAlbumArtUrl(isrc, track, artist)


}
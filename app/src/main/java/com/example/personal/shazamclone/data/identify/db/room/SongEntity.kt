package com.example.personal.shazamclone.data.identify.db.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey


/**
 * Created by personal on 4/20/2018.
 */

@Entity(tableName = "songData")
 class SongEntity{


        @PrimaryKey(autoGenerate = true)
        var id: Long? = null

        @ColumnInfo(name = "name")
        var name : String = ""

        @ColumnInfo(name = "artist")
        var artist : String = ""

        @ColumnInfo(name = "album")
        var album : String = ""

        @ColumnInfo(name = "youtubeLink")
        var vidId : String = ""

    constructor()

    @Ignore
    constructor(name: String, artist: String, album: String, vidId:String): this(){

        this.name = name
        this.artist = artist
        this.album = album
        this.vidId = vidId
    }

}
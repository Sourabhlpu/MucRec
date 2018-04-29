package com.example.personal.shazamclone.data.identify.db.room

import android.arch.persistence.room.*


/**
 * Created by personal on 4/20/2018.
 */

@Entity(tableName = "songData", indices = arrayOf(Index(value = "youtubeLink", unique = true)))
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

        @ColumnInfo(name = "imageUrl")
        var imageUrl : String = ""

    constructor()

    @Ignore
    constructor(name: String, artist: String, album: String, vidId:String, imageUrl : String = "")
            : this(){

        this.name = name
        this.artist = artist
        this.album = album
        this.vidId = vidId
        this.imageUrl = imageUrl
    }

}
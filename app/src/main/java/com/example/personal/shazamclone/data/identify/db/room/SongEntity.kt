package com.example.personal.shazamclone.data.identify.db.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/**
 * Created by personal on 4/20/2018.
 */

@Entity(tableName = "songData")
 data class SongEntity(
        @PrimaryKey(autoGenerate = true) var id: Long?,
        @ColumnInfo(name = "song_name") var name : String,
        @ColumnInfo(name = "song_album") var album : String,
        @ColumnInfo(name = "song_artists") var artists : String,
        @ColumnInfo(name = "youtube_vidId") var vidId : String
) {

    constructor() : this(0, "", "", "", "")

}
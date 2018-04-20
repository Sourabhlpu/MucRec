package com.example.personal.shazamclone.data.identify.db.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.example.personal.shazamclone.utils.App

/**
 * Created by personal on 4/20/2018.
 */
@Database(entities = arrayOf(SongEntity :: class), version = 1)

abstract class SongDatabase : RoomDatabase() {

    abstract fun songDao() : SongDao

    companion object {
        val instance : SongDatabase by lazy {
            Room.databaseBuilder(App.instance, SongDatabase :: class.java, "song.db")
                    .build()
        }
    }

}
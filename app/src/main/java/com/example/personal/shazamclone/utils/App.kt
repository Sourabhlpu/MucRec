package com.example.personal.shazamclone.utils

import android.app.Application
import android.arch.persistence.room.Room
import com.example.personal.shazamclone.data.identify.db.room.SongDatabase

/**
 * Created by personal on 4/15/2018.
 */
class App : Application() {

    companion object {

        lateinit var instance: App

            private set
        lateinit var database : SongDatabase
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        database = Room.databaseBuilder(instance,SongDatabase :: class.java, "song.db").build()
    }
}
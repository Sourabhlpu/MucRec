package com.example.personal.shazamclone.data.identify.db

import org.jetbrains.anko.db.select

/**
 * Created by personal on 4/20/2018.
 */
class SongDb(private val songDbHelper: SongDbHelper = SongDbHelper.instance){

    fun requestAllSavedSongs() = songDbHelper.use {

        val songList = select(SongTable.NAME)
                .whereArgs("${SongTable.TITLE} != null")
    }
}
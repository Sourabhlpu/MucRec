/*
package com.example.personal.shazamclone.data.identify.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.personal.shazamclone.utils.App
import org.jetbrains.anko.db.*

*/
/**
 * Created by personal on 4/18/2018.
 *//*


class SongDbHelper(ctx : Context  = App.instance) : ManagedSQLiteOpenHelper(ctx,
        SongDbHelper.DB_NAME, null, SongDbHelper.DB_VERSION){


    companion object {

       val DB_NAME = "song.db"
       val DB_VERSION = 1
        val instance by lazy { SongDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase) {

        db.createTable(SongTable.NAME, true,
                SongTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                SongTable.TITLE to TEXT,
                SongTable.ALBUM to TEXT,
                SongTable.ARTISTS to TEXT,
                SongTable.VIDEOID to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.dropTable(SongTable.NAME, true)
        onCreate(db)
    }
}*/

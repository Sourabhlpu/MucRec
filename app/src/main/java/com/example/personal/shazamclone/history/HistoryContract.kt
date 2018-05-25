package com.example.personal.shazamclone.history

import com.example.personal.shazamclone.BasePresenter
import com.example.personal.shazamclone.BaseView
import com.example.personal.shazamclone.data.identify.db.room.SongEntity

/**
 * Created by personal on 4/23/2018.
 * A contract between the HistoryFragment and the HistoryPresenter
 */

interface HistoryContract{

    // interface to be implemented by the Fragment.

    interface view : BaseView<Presenter>{

        //this function is responsible for updating the adapter with the song data.

        fun showDiscoveredSongs(songs : List<SongEntity>)

        // method's names are self explainatory to tell what they do
        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorView()

        fun hideErrorView()
    }


    // to be implemented by the Presenter
    interface Presenter : BasePresenter<view>{

        // this method will be implemented by the presenter which will query the database to fetch
        // all the songs that are saved.
        fun fetchSongs() : List<SongEntity>?
    }
}
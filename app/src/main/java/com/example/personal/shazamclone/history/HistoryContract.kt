package com.example.personal.shazamclone.history

import com.example.personal.shazamclone.BasePresenter
import com.example.personal.shazamclone.BaseView
import com.example.personal.shazamclone.data.identify.db.room.SongEntity

/**
 * Created by personal on 4/23/2018.
 */

interface HistoryContract{

    interface view : BaseView<Presenter>{

        //this function is responsible for updating the adapter with the song data.

        fun showDiscoveredSongs(songs : List<SongEntity>)

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorView()

        fun hideErrorView()
    }

    interface Presenter : BasePresenter<view>{

        fun fetchSongs() : List<SongEntity>?



    }
}
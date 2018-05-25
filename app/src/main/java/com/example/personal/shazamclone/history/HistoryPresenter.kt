package com.example.personal.shazamclone.history

import com.example.personal.shazamclone.data.identify.db.ShazamRepository
import com.example.personal.shazamclone.data.identify.db.room.SongEntity

/**
 * Created by personal on 4/23/2018.
 * As usual another presenter with the same old code. By now I don't need to write comments for it
 * Nothing very interesting here anyways.
 */


class HistoryPresenter: HistoryContract.Presenter {

    private lateinit var mHistoryView : HistoryContract.view

    override fun takeView(view: HistoryContract.view) {

        mHistoryView = view

        mHistoryView.setPresenter(this)
    }

    override fun dropView() {


    }

    // the only interesting thing about this presenter. This method calls repository to get the
    // list of all the songs in the database.
    override fun fetchSongs(): List<SongEntity>? = ShazamRepository.instance.getAllSongs()



}
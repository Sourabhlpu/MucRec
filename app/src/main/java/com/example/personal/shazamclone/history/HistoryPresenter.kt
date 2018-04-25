package com.example.personal.shazamclone.history

import com.example.personal.shazamclone.data.identify.db.ShazamRepository
import com.example.personal.shazamclone.data.identify.db.room.SongEntity

/**
 * Created by personal on 4/23/2018.
 */


class HistoryPresenter: HistoryContract.Presenter {

    private lateinit var mHistoryView : HistoryContract.view

    override fun takeView(view: HistoryContract.view) {

        mHistoryView = view

        mHistoryView.setPresenter(this)
    }

    override fun dropView() {


    }

    override fun fetchSongs(): List<SongEntity>? = ShazamRepository.instance.getAllSongs()



}
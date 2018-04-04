package com.example.personal.shazamclone.discover

import android.app.Application
import android.content.Context
import android.content.Intent
import com.example.personal.shazamclone.data.identify.SongIdentifyService
import com.example.personal.shazamclone.data.identify.SongIdentifyService.SongIdentificationCallback
import com.example.personal.shazamclone.domain.Song

/**
 * Created by personal on 3/26/2018.
 */

class DiscoverPresenter(private val songIdentifyService : SongIdentifyService) : DiscoverContract.Presenter,
        SongIdentificationCallback
{

    private lateinit var mDiscoverView : DiscoverContract.View





    override fun takeView(view: DiscoverContract.View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        mDiscoverView = view

    }

    override fun dropView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartIdentifyButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        //start the service here
        val intent = Intent(, SongIdentifyService :: class.java)

    }

    override fun onStopIdentifyButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        //stop the service here
    }

    override fun onDonateButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onHistoryButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onOfflineError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGenericError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSongNotFound() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSongFound(song: Song) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
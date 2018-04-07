package com.example.personal.shazamclone.discover

import com.example.personal.shazamclone.data.identify.SongIdentifyService.SongIdentificationCallback
import com.example.personal.shazamclone.domain.Song

/**
 * Created by personal on 3/26/2018.
 */

class DiscoverPresenter: DiscoverContract.Presenter,
        SongIdentificationCallback
{

    private lateinit var mDiscoverView : DiscoverContract.View


    override fun takeView(view: DiscoverContract.View) {


        mDiscoverView = view

        mDiscoverView.setPresenter(this)

    }

    override fun dropView() {

        mDiscoverView.hashCode()
    }

    override fun onStartIdentifyButtonClicked() {


        //start the service here
        mDiscoverView.startSongIdentifyService()

        mDiscoverView.hideStartIdentifyButtonView()
        mDiscoverView.showStopIdentifyButtonView()
        mDiscoverView.showIdentifyProgressView()
        mDiscoverView.hideErrorViews()
    }

    override fun onStopIdentifyButtonClicked() {


        //stop the service here
        mDiscoverView.stopSongIdentifyService()


        mDiscoverView.hideIdentifyProgressView()
        mDiscoverView.hideStopIdentifyButtonView()
        mDiscoverView.showStartIdentifyButtonView()
    }

    override fun onDonateButtonClicked() {

        mDiscoverView.openDonatePage()
    }

    override fun onHistoryButtonClicked() {

        mDiscoverView.openHistoryPage()
    }

    override fun onOfflineError() {

    }

    override fun onGenericError() {


        mDiscoverView.hideStartIdentifyButtonView()
        mDiscoverView.showStopIdentifyButtonView()
        mDiscoverView.showIdentifyProgressView()
        mDiscoverView.hideErrorViews()

        mDiscoverView.showIdentifyProgressView()

        // And since the MusicIdentifyService could not identify a song because of a generic error,
        // ensure that a call was made to show an offline error view
        mDiscoverView.showGenericErrorView()
    }

    override fun onSongNotFound() {


        mDiscoverView.hideStartIdentifyButtonView()
        mDiscoverView.showStopIdentifyButtonView()
        mDiscoverView.showIdentifyProgressView()
        mDiscoverView.hideErrorViews()
        mDiscoverView.showIdentifyProgressView()

        mDiscoverView.showNotFoundErrorView()

    }

    override fun onSongFound(song: Song) {

        mDiscoverView.hideStartIdentifyButtonView()
        mDiscoverView.showStopIdentifyButtonView()
        mDiscoverView.showIdentifyProgressView()
        mDiscoverView.hideErrorViews()

        mDiscoverView.openSongDetailPage(song)
    }

}
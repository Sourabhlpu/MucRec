package com.example.personal.shazamclone.discover

/**
 * Created by personal on 3/26/2018.
 */

class DiscoverPresenter: DiscoverContract.Presenter

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


}
package com.example.personal.shazamclone.discover

import com.example.personal.shazamclone.BasePresenter
import com.example.personal.shazamclone.BaseView
import com.example.personal.shazamclone.data.identify.db.room.SongEntity
import com.example.personal.shazamclone.domain.Song

/**
 * Created by personal on 3/26/2018.
 * This interface defines the contract between the discoverFragment and discover Presenter
 * Here we define the methods that will be overriden by the fragment and the presenter.
 */

interface  DiscoverContract{

    //this interface will be implemented by the discover fragment. To enable the fragment to be able
    // to call the methods of the presenter defined in this contract we make the View extend the
    // BaseView

    interface  View: BaseView<Presenter> {

        // Show/hide progress view
        fun showIdentifyProgressView()
        fun hideIdentifyProgressView()

        // Show/hide start button
        fun showStartIdentifyButtonView()
        fun hideStartIdentifyButtonView()

        // Show/hide stop button
        fun showStopIdentifyButtonView()
        fun hideStopIdentifyButtonView()

        // Show error views
        fun showOfflineErrorView()
        fun showGenericErrorView()
        fun showNotFoundErrorView()
        // Function to hide them all (Lazy)
        fun hideErrorViews()

        // Open the pages (Activities)
        fun openSongDetailPage(song: Song)
        fun openDonatePage()
        fun openHistoryPage()

        // start or stop the service
        fun startSongIdentifyService()
        fun stopSongIdentifyService()
    }

    //this interface will be implemented by the discover presenter
    interface Presenter: BasePresenter<View> {

        // Called when the user clicks on the start button
        fun onStartIdentifyButtonClicked()

        // Called when the user clicks on the stop button
        fun onStopIdentifyButtonClicked()

        // Called when the user clicks on the donate button
        fun onDonateButtonClicked()

        // Called when the user clicks on the history button
        fun onHistoryButtonClicked()

       // This function is required to save the discovered song to the local database
        fun saveSongLocally(song : SongEntity)
    }
}
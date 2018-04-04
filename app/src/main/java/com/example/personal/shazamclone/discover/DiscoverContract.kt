package com.example.personal.shazamclone.discover

import com.example.personal.shazamclone.BasePresenter
import com.example.personal.shazamclone.BaseView
import com.example.personal.shazamclone.domain.Song

/**
 * Created by personal on 3/26/2018.
 */

interface  DiscoverContract{

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
    }

    interface Presenter: BasePresenter<View> {

        // Called when the user clicks on the start button
        fun onStartIdentifyButtonClicked()

        // Called when the user clicks on the stop button
        fun onStopIdentifyButtonClicked()

        // Called when the user clicks on the donate button
        fun onDonateButtonClicked()

        // Called when the user clicks on the history button
        fun onHistoryButtonClicked()

    }
}
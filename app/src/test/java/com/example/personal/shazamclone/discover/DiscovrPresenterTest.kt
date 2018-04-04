package com.example.personal.shazamclone.discover
import com.example.personal.shazamclone.domain.Song
import com.example.personal.shazamclone.data.identify.SongIdentifyService
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.After
import org.junit.Before
import org.junit.Test


/**
 * Created by personal on 3/27/2018.
 */

class DiscoverPresenterTest{

    //variable holding a simple song object we will be using in some functions;
    private val mSong = Song()

    private val mSongIdentifyService : SongIdentifyService = mock()

    private val mDiscoverView: DiscoverContract.View = mock()

    private lateinit var mDiscoverPresenter: DiscoverContract.Presenter

    @Before
    fun setupDiscoverPresenter()
    {
        mDiscoverPresenter = DiscoverPresenter(mSongIdentifyService)
        mDiscoverPresenter.takeView(mDiscoverView)
    }

    @After
    fun releasePresenter(){

        mDiscoverPresenter.dropView()
    }

    @Test
    fun onStartIdentifyButtonClickedAndSongWasFoundOpensSongDetailPage(){

        mDiscoverPresenter.onStartIdentifyButtonClicked()

        argumentCaptor<SongIdentifyService.SongIdentificationCallback>().apply {

            verify(mSongIdentifyService).startIdentification(capture())

            firstValue.onSongFound(mSong)
        }

        verify(mDiscoverView).hideStartIdentifyButtonView()
        verify(mDiscoverView).showStopIdentifyButtonView()
        verify(mDiscoverView).showIdentifyProgressView()
        verify(mDiscoverView).hideErrorViews()

        verify(mDiscoverView).openSongDetailPage(mSong)
    }

    @Test
     fun onStartIdentifyButtonClickedAndSongWasNotFoundShowsSongNotFoundError()
    {
        mDiscoverPresenter.onStartIdentifyButtonClicked()

        argumentCaptor<SongIdentifyService.SongIdentificationCallback>().apply {

            verify(mSongIdentifyService).startIdentification(capture())

            firstValue.onSongNotFound()
        }


        verify(mDiscoverView).hideStartIdentifyButtonView()
        verify(mDiscoverView).showStopIdentifyButtonView()
        verify(mDiscoverView).showIdentifyProgressView()
        verify(mDiscoverView).hideErrorViews()
        verify(mDiscoverView).showIdentifyProgressView()

        // And since the MusicIdentifyService could not identify the song,
        // ensure that a call was made to show a song not found error view
        verify(mDiscoverView).showNotFoundErrorView()
    }

    @Test
     fun onStartIdentifyButtonClickedAndGenericErrorShowsGenericErrorView(){

        mDiscoverPresenter.onStartIdentifyButtonClicked()

        argumentCaptor<SongIdentifyService.SongIdentificationCallback>().apply {

            verify(mSongIdentifyService).startIdentification(capture())

            firstValue.onGenericError()
        }

        verify(mDiscoverView).hideStartIdentifyButtonView()
        verify(mDiscoverView).showStopIdentifyButtonView()
        verify(mDiscoverView).showIdentifyProgressView()
        verify(mDiscoverView).hideErrorViews()

        verify(mDiscoverView).showIdentifyProgressView()

        // And since the MusicIdentifyService could not identify a song because of a generic error,
        // ensure that a call was made to show an offline error view
        verify(mDiscoverView).showGenericErrorView()
    }

    @Test
    fun onStopIdentifyButtonClicked() {
        mDiscoverPresenter.onStopIdentifyButtonClicked()

        // Ensure that when stop identify button is clicked, music identification is stopped, progress view is hidden,
        // stop discovering button is hidden and start discovering button is shown
        verify(mSongIdentifyService).stopIdentification()
        verify(mDiscoverView).hideIdentifyProgressView()
        verify(mDiscoverView).hideStopIdentifyButtonView()
        verify(mDiscoverView).showStartIdentifyButtonView()
    }

    @Test
    fun onDonateButtonClicked() {
        mDiscoverPresenter.onDonateButtonClicked()
        // Ensure that when the donate button is clicked, a call to open the donate page is made
        verify(mDiscoverView).openDonatePage()
    }

    @Test
    fun onHistoryButtonClicked() {
        mDiscoverPresenter.onHistoryButtonClicked()
        // Ensure that when the history button is clicked, a call to open the history page is made
        verify(mDiscoverView).openHistoryPage()
    }

}
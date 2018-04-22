package com.example.personal.shazamclone.discover

import android.util.Log
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.data.identify.db.ShazamRepository
import com.example.personal.shazamclone.domain.Song
import com.example.personal.shazamclone.utils.App
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchListResponse
import java.io.IOException


/**
 * Created by personal on 3/26/2018.
 */

class DiscoverPresenter: DiscoverContract.Presenter

{


    private lateinit var mDiscoverView : DiscoverContract.View




    companion object {

        private val youtube : YouTube by lazy {

            YouTube.Builder(NetHttpTransport(), JacksonFactory(), HttpRequestInitializer {  })
                    .setApplicationName(App.instance.getString(R.string.app_name)).build()
        }


        private lateinit var query: YouTube.Search.List

        private fun initQuery()
        {
            try {
                query = youtube.search().list("id,snippet")
                query.key = App.instance.getString(R.string.youtube_api_key)
                query.type = "video"
                query.fields = "items(id/videoId,snippet/title)"
            }catch (e : IOException){

                Log.d("Discover Presenter", "Could not initialize: " + e)
            }
        }

        fun searchYoutubeAndGetVidId(keywords : String) : String {

            initQuery()

            lateinit var vidId : String

            query.setQ(keywords)

            try{

                val response : SearchListResponse = query.execute()

                 vidId = response.items.get(0).id.videoId

                Log.d("DiscoverPresenter ", "video id is $vidId")
            }
            catch (e : IOException)
            {
                Log.d("Discover Presenter", "Could not search: " + e)
            }

            return vidId
        }


    }


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


    override fun saveSongLocally(song: Song) {

        ShazamRepository.instance.saveSong(song)
    }
}
package com.example.personal.shazamclone.discover

import android.util.Log
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.data.identify.db.ShazamRepository
import com.example.personal.shazamclone.data.identify.db.room.SongEntity
import com.example.personal.shazamclone.utils.App
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchListResponse
import java.io.IOException


/**
 * Created by personal on 3/26/2018.
 * This is a presenter for the DiscoverFragment. All the heavy coding is supposed to be done by the
 * presenter. Presenter should also act as a bridge between the repository and the view.
 *
 * To know more about how to implement the youtube search for getting the vid id go to the following
 * tutorial : https://code.tutsplus.com/tutorials/create-a-youtube-client-on-android--cms-22858
 */

//the presenter implements Presenter interface defined in the contract
class DiscoverPresenter: DiscoverContract.Presenter

{

    //we need a reference to the View so that we can call methods that are implemented(from the contract) in the fragment.
    private lateinit var mDiscoverView : DiscoverContract.View


    //although everything in Kotlin is a object but if we need to create static types in kotlin we need
    // to use companion object
    companion object {

        //using lazy delegate to initialize the variable. This also initializes the object only once
        // and then returns the same object whenever called again thus a lot more efficient.
        private val youtube : YouTube by lazy {

            YouTube.Builder(NetHttpTransport(), JacksonFactory(), HttpRequestInitializer {  })
                    .setApplicationName(App.instance.getString(R.string.app_name)).build()
        }


        //initializing the query object
        private lateinit var query: YouTube.Search.List
        private set

        // this method initializes the query variable
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

        // we call this function when we don't get any youtube vidId from the ACRCloud's response.
        // we simply do a youtube search with the song name, artist and album and then get the video
        // id from the result. We assume that the first video will be the official one which is not
        // always true but still works in most cases.

        // there can be a better way of doing this but for now this works
        fun searchYoutubeAndGetVidId(keywords : String) : String {


            // we need to call the initQuery() method to initialize the query object before we can execute
            // the query
            initQuery()

            // property to store the vidId
            lateinit var vidId : String

            //setting the keywords to do the search
            query.setQ(keywords)

            try{

                // executing the query and collecting the respose
                val response : SearchListResponse = query.execute()

                // we are just getting the vid id of the first result we get in the list
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

    // since DiscoverPresenter implements the DiscoverContract.Presenter we have to implement this method
    // this simply initializes the mDiscoverView so that we can call methods from the fragment.

    override fun takeView(view: DiscoverContract.View) {


        mDiscoverView = view


        //setting the presenter for the view here and passing the reference of this presenter.
        mDiscoverView.setPresenter(this)

    }

    // to be implemented later
    override fun dropView() {

        mDiscoverView.hashCode()
    }

    // this method is called as soon as the user taps to identify the song.
    override fun onStartIdentifyButtonClicked() {


        //we will call the method in the fragment that starts the service to identify the song
        // we are actually not creating any service on our own as all the code of the acrcloud android
        // sdk runs on a backgroud thread
        mDiscoverView.startSongIdentifyService()

        //again showing/hiding the relevant views
        mDiscoverView.hideStartIdentifyButtonView()
        mDiscoverView.showStopIdentifyButtonView()
        mDiscoverView.showIdentifyProgressView()
        mDiscoverView.hideErrorViews()
    }

    //When the user wants to abruptly stop the identification of the song
    override fun onStopIdentifyButtonClicked() {


        //stop the service here
        mDiscoverView.stopSongIdentifyService()


        //hiding/showing the relevant views
        mDiscoverView.hideIdentifyProgressView()
        mDiscoverView.hideStopIdentifyButtonView()
        mDiscoverView.showStartIdentifyButtonView()
    }

    //opens the donate page which isn't implemented yet
    override fun onDonateButtonClicked() {

        mDiscoverView.openDonatePage()
    }

    //When we click the history buttong we need to open the history page to see the list of songs
    // that we identifited before
    override fun onHistoryButtonClicked() {

        //this will call the method in the fragment to open the history page using the intents.
        mDiscoverView.openHistoryPage()
    }


    //this method calls the repository to save the song that was discovered save locally
    override fun saveSongLocally(song: SongEntity) {

        Log.d("DiscoverPresenter", "savSongLocally from presenter called ")

        //calling the repository to save the song
        ShazamRepository.instance.saveSong(song)
    }
}
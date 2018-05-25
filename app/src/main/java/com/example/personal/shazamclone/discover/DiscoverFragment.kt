package com.example.personal.shazamclone.discover


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.acrcloud.rec.sdk.ACRCloudClient
import com.acrcloud.rec.sdk.ACRCloudConfig
import com.acrcloud.rec.sdk.IACRCloudListener
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.data.identify.ResponseClasses
import com.example.personal.shazamclone.data.identify.ResponseClasses.SongIdentificationResult
import com.example.personal.shazamclone.data.identify.SongIdentificationCallback
import com.example.personal.shazamclone.domain.MusicDataMapper
import com.example.personal.shazamclone.domain.Song
import com.example.personal.shazamclone.history.HistoryActivity
import com.example.personal.shazamclone.songdetail.SongDetailActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.fragment_discover.view.*


/**
 * A simple [Fragment] subclass.
 * this fragment is the home screen of the app. It implements the DiscoverContracts view interface,
 * the ACRCloudListener and the SongIdentification callback.
 *
 */ class DiscoverFragment : Fragment(),DiscoverContract.View, IACRCloudListener, SongIdentificationCallback {

    // a variable to hold the reference to the presenter interface. lateinit will just delay the initialization
    private lateinit var mPresenter : DiscoverContract.Presenter

    //a variable that is needed to call the ACRCloud api for music recognition. It is initialized by
    // a lazy delegate
    private val mClient : ACRCloudClient by lazy { ACRCloudClient() }

    //this is a configuration object which helps in setting the configuration settings for the acr client
    // this is again initialized by the lazy delegate
    private val mConfig : ACRCloudConfig by lazy { ACRCloudConfig() }

    private var initState : Boolean = false
    private var mProcessing : Boolean = false

    private val handler : Handler by lazy{ Handler(Looper.getMainLooper())}


    //this method helps us initialize the mPresenter variable so that we can call the methods
    // overriden by the presenter.
    override fun setPresenter(presenter: DiscoverContract.Presenter) {

        mPresenter = presenter
    }


    //this method starts the ripple  animation
    override fun showIdentifyProgressView() {

        Log.d("DiscoverFragment", "showing identifyProgressView")

        discoverIdentifyProgressView.visibility = View.VISIBLE
    }

    // this method hides the ripple animation
    override fun hideIdentifyProgressView() {

        discoverIdentifyProgressView.visibility = View.GONE

        Log.d("DiscoverFragment", "hiding identifyProgressView")
    }

    // showing an image button which starts the recognition
    override fun showStartIdentifyButtonView() {

        discoverStartIdentifyButton.visibility = View.VISIBLE

        Log.d("DiscoverFragment", "showing startIdentifyButtonView")
    }

    // hiding the start identification button
    override fun hideStartIdentifyButtonView() {

        discoverStartIdentifyButton.visibility = View.GONE

        Log.d("DiscoverFragment", "hiding startIdentifyButtonView")
    }

    // showing the button that stops the identification
    override fun showStopIdentifyButtonView() {

        discoverStopIdentifyButton.visibility = View.VISIBLE

        Log.d("DiscoverFragment", "showing stopIdentifyButtonView")
    }

    //hiding the button that stops the identification
    override fun hideStopIdentifyButtonView() {

        discoverStopIdentifyButton.visibility = View.GONE

        Log.d("DiscoverFragment", "hiding stopIdentifyButtonView")
    }

    //error view that is shown when the device is offline
    override fun showOfflineErrorView() {

        Log.d("DiscoverFragment", "Offline Error")
        error_tv.visibility = View.VISIBLE
        error_tv.text = "No internet connection"

    }

    // shows an error when we cannot identifiy any specific error
    override fun showGenericErrorView() {

        Log.d("DiscoverFragment", "Generic Error")
        error_tv.visibility = View.VISIBLE
        error_tv.text = "Unknown Error"

    }

    //this method is called when the song could not be identified and we set the error message
    override fun showNotFoundErrorView() {

        Log.d("DiscoverFragment", "Not Found Error")
        error_tv.text = "Song not found"
        error_tv.visibility = View.VISIBLE

    }

    // this method hides the error view
    override fun hideErrorViews() {

        Log.d("DiscoverFragment", "hide all errors")
        error_tv.visibility = View.INVISIBLE
    }

    // once the song is identifiecd successfully we open the song details page.
    override fun openSongDetailPage(song: Song) {

        Log.d("DiscoverFragment", "album " + song.album + ", artist " + song.artist +
        ", name " + song.name)

        val intent = Intent(activity, SongDetailActivity::class.java)

        //have to pass the song object so as to display the song details;

        intent.putExtra(getString(R.string.song_name_extra), song.name)

        intent.putExtra(getString(R.string.song_album_extra), song.album)

        intent.putExtra(getString(R.string.song_artist_extra), song.artist)

        intent.putExtra(getString(R.string.song_youtube_id), song.youtubeLink)

        intent.putExtra(getString(R.string.isrc_id_extra), song.isrc)

        hideIdentifyProgressView()

        startActivity(intent)

    }

    // haven't implemented this page yet
    override fun openDonatePage() {
        val intent = Intent(activity, HistoryActivity::class.java)
        startActivity(intent)
    }

    // this method opens up the history page where we can see the history of discovered pages.
    override fun openHistoryPage() {

        val intent = Intent(activity, HistoryActivity ::class.java)

        startActivity(intent)
    }


    // in this function we setup the config object. Since we are using a lazy delegate. As soon as
    // the mConfig's set method is called(which happens in this function) the mConfig's object is created
    // the config object has all the settings for the client
    fun setUpConfig(){

        Log.d("DiscoverFragment", "setupConfig called")


        this.mConfig.acrcloudListener = this@DiscoverFragment

        this.mConfig.host = "identify-eu-west-1.acrcloud.com"
        this.mConfig.accessKey = "ff98c0119a6fc307cde6e3708b6eeac6"
        this.mConfig.accessSecret = "fC1U4vP1eT5X24dIqrmnUB9px1t4LTZRmzQCC8Tm"
        this.mConfig.protocol = ACRCloudConfig.ACRCloudNetworkProtocol.PROTOCOL_HTTP // PROTOCOL_HTTPS
        this.mConfig.reqMode = ACRCloudConfig.ACRCloudRecMode.REC_MODE_REMOTE

    }

    // in this method we add the config object to the client and then initialize the client
    fun addConfigToClient(){

        Log.d("DiscoverFragment", "addConfigToClient called")


        this.initState = this.mClient.initWithConfig(this.mConfig) // returns true when initialized successfully

        if(this.initState)
        {
            //Pre-record audio via microphone, it makes recognition much faster (recommended).
            this.mClient.startPreRecord(3000)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var rootView = inflater.inflate(R.layout.fragment_discover, container, false)

        // here we set the onClickListeners to start/stop the identification

        //we set the click listener on the startIdentification button
        rootView.discoverStartIdentifyButton.setOnClickListener {

            //then in the on click method this like of code is called. It just uses the presenter
            // reference to call the relevant method in the presenter
            mPresenter.onStartIdentifyButtonClicked()
        }

        // we set the click listener on the stopIdentification button
        rootView.discoverStopIdentifyButton.setOnClickListener{

            //then in the on click method this like of code is called. It just uses the presenter
            // reference to call the relevant method in the presenter
            mPresenter.onStopIdentifyButtonClicked()
        }

        // her we just set the clickListener on the history button
        rootView.discoverHistoryButton.setOnClickListener{

            // calling the presenter method
            mPresenter.onHistoryButtonClicked()
        }


        return rootView
    }



    // Called to start identifying/discovering the song that is currently playing
    fun startIdentification(callback: SongIdentificationCallback)
    {

        Log.d("DiscoverFragment", "startIdentification called")


        //check if the mClient was initialized properly
        if(!initState)
        {
            Log.d("AcrCloudImplementation", "init error")
        }

        //check here to see if we are already processing to do a recognition
        if(!mProcessing) {

            mProcessing = true

            // recognition starts here. it returns false if there is some error
            if (!mClient.startRecognize()) {

                mProcessing = false
                Log.d("AcrCloudImplementation" , "start error")

            }
        }
    }

    // Called to stop identifying/discovering song
    fun stopIdentification()
    {

        Log.d("DiscoverFragment", "stopIdentification called")

        // if its already processing stop recognizing the song
        if(mProcessing)
        {
            //This function is for humming recognition only, it will stop the recording and start
            // recognizing immediately.
            mClient.stopRecordToRecognize()
        }

        mProcessing = false
    }

    fun cancelListeningToIdentifySong()
    {
        if(mProcessing)
        {
            mProcessing = false
            //This function will cancel the recognition immediately.
            mClient.cancel()
        }
    }


    // this method is overriden as this fragment implements the ACRCloudListener. onResult is called
    // when get a response from the api.
    override fun onResult(resultString: String?) {

        Log.d("DiscoverFragment", "onResult called")
        Log.d("DiscoverFragment",resultString)

        //got the response cancel the recognition
        mClient.cancel()
        mProcessing = false

        // we use GSON library for mapping the response into a class object
        val result = Gson().fromJson(resultString, ResponseClasses.SongIdentificationResult:: class.java)

        Log.d("DiscoverFragment", "parsed result " + result)

        // for offline error
        if(result.status.code == 3000)
        {
            onOfflineError()
        }
        //shows not found error
        else if(result.status.code == 1001)
        {
            onSongNotFound()
        }
        //if the status code is 0 then we know that we got the result
        else if(result.status.code == 0 )
        {
            // now we need the youtube vid id of the track for he song details activity.
            // we check if we got the youtube vid id. if not we need to do some extra work

            // if we have the video id then all is good.
            if(result.metadata.music.get(0).external_metadata.youtube != null) {

                Log.d("DiscoverFragment", "get youtube vid id from ACRCloud")

                // the conversion from result to a Song object is easy
                onSongFound(MusicDataMapper().convertFromDataModel(result))
            }
            // if we don't have the vid id then we need to do a youtube search
            else
            {

                Log.d("DiscoverFragment", "didn't get youtube vid id from ACRCloud")
                // we want to do a youtube search using the song name and album name. This is done
                // in a background thread
                startThreadToSearchYoutube(result.metadata.music.get(0).title,
                        result.metadata.music.get(0).album.name, result)
            }
        }
        else
        {
            onGenericError()
        }


    }

    override fun onVolumeChanged(p0: Double) {

    }

    // this method will be called by the presenter to start identification
    override fun startSongIdentifyService() {

        setUpConfig()
        addConfigToClient()

        startIdentification(this@DiscoverFragment)

    }

    // this method will be called by the presenter to stop the identification
    override fun stopSongIdentifyService() {

        stopIdentification()
        cancelListeningToIdentifySong()
    }

    override fun onPause() {
        super.onPause()

        //mPresenter.dropView()
    }

    //called by the presenter to show offline error

    override fun onOfflineError() {
        hideStopIdentifyButtonView()
        showStartIdentifyButtonView()
        hideIdentifyProgressView()

        showOfflineErrorView()
    }

    //called by the presenter to show the generic error

    override fun onGenericError() {
        hideStartIdentifyButtonView()
        showStopIdentifyButtonView()
        showIdentifyProgressView()
        hideErrorViews()

        showIdentifyProgressView()

        // And since the MusicIdentifyService could not identify a song because of a generic error,
        // ensure that a call was made to show an offline error view
        showGenericErrorView()
    }

    //called by the presenter to show song not found error

    override fun onSongNotFound() {

        hideStopIdentifyButtonView()
        showStartIdentifyButtonView()
        hideIdentifyProgressView()

        showNotFoundErrorView()
    }

    // this method is overridden here which was defined in the contract. This method is called
    // one we have the song object created after the recognition is complented
    override fun onSongFound(song: Song) {

        //we need to convert the song object into a songEntity object so that we can save it to the
        // database
        val songEntity = MusicDataMapper().convertSongToEntity(song)

        Log.d("DiscoverFragment", "onSongFound, ${songEntity.name} ")

        // calling the presenter method to save the song entity;
        mPresenter.saveSongLocally(songEntity)

        //just hiding/showing the relevant views
        hideStartIdentifyButtonView()
        showStopIdentifyButtonView()
        showIdentifyProgressView()
        hideErrorViews()

        // we have the song lets open the song's detail page
        openSongDetailPage(song)

    }


    //this tread will be execute as we don't have the youtube vid id of the track. So we need to
    // do a youtube search with the song name and the album

    // Note: I need to change this to use the loader as I have to update the ui and loaders are the
    // best option to do it(well unless i am trying the android architecture components)
    fun startThreadToSearchYoutube(title : String, album : String, result : SongIdentificationResult)
    {
        Log.d("DiscoverFragment", "startThreadToSearchYoutube called")
       object : Thread(){

          override fun run(){

              // calling the presenter method to get the vid id

              val  vidId = DiscoverPresenter.searchYoutubeAndGetVidId("$title, $album")

                System.out.println("DiscoverFragment, got the vid id from thread $vidId")

              // as we need to update the views we have to call the handler of the main thread and update
              // the ui
                handler.post{

                    // now that we have the video id of the track we can convert the result into a
                    // song object and call the onSongFound method.
                    onSongFound(MusicDataMapper().convertFromDataModel(result, vidId))
                }


            }
        }.start()

    }


}

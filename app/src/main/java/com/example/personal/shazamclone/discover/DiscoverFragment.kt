package com.example.personal.shazamclone.discover


import android.content.Intent
import android.os.Bundle
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
 */ class DiscoverFragment : Fragment(),DiscoverContract.View, IACRCloudListener, SongIdentificationCallback {

    private lateinit var mPresenter : DiscoverContract.Presenter
    private val mClient : ACRCloudClient by lazy { ACRCloudClient() }
    private val mConfig : ACRCloudConfig by lazy { ACRCloudConfig() }
    private var initState : Boolean = false
    private var mProcessing : Boolean = false


    override fun setPresenter(presenter: DiscoverContract.Presenter) {

        mPresenter = presenter
    }


    override fun showIdentifyProgressView() {

        discoverIdentifyProgressView.visibility = View.VISIBLE
    }

    override public fun hideIdentifyProgressView() {

        discoverIdentifyProgressView.visibility = View.GONE
    }

    override fun showStartIdentifyButtonView() {

        discoverStartIdentifyButton.visibility = View.VISIBLE
    }

    override fun hideStartIdentifyButtonView() {

        discoverStartIdentifyButton.visibility = View.GONE
    }

    override fun showStopIdentifyButtonView() {

        discoverStopIdentifyButton.visibility = View.VISIBLE
    }

    override fun hideStopIdentifyButtonView() {

        discoverStopIdentifyButton.visibility = View.GONE
    }

    override fun showOfflineErrorView() {

        Log.d("DiscoverFragment", "Offline Error")
    }

    override fun showGenericErrorView() {

        Log.d("DiscoverFragment", "Generic Error")
    }

    override fun showNotFoundErrorView() {

        Log.d("DiscoverFragment", "Not Found Error")
    }

    override fun hideErrorViews() {

        Log.d("DiscoverFragment", "hide all errors")
    }

    override fun openSongDetailPage(song: Song) {

        Log.d("DiscoverFragment", "album " + song.album + ", artist " + song.artist +
        ", name " + song.name)

        val intent = Intent(activity, SongDetailActivity::class.java)

        //have to pass the song object so as to display the song details;

        intent.putExtra(getString(R.string.song_name_extra), song.name)

        intent.putExtra(getString(R.string.song_album_extra), song.album)

        intent.putExtra(getString(R.string.song_artist_extra), song.artist)

        startActivity(intent)

    }

    override fun openDonatePage() {
        val intent = Intent(activity, HistoryActivity::class.java)
        startActivity(intent)
    }

    override fun openHistoryPage() {

        val intent = Intent(activity, HistoryActivity ::class.java)

        startActivity(intent)
    }






    fun setUpConfig(){

        Log.d("DiscoverFragment", "setupConfig called")

        this.mConfig.acrcloudListener = this@DiscoverFragment

        this.mConfig.host = "identify-eu-west-1.acrcloud.com"
        this.mConfig.accessKey = "ff98c0119a6fc307cde6e3708b6eeac6"
        this.mConfig.accessSecret = "fC1U4vP1eT5X24dIqrmnUB9px1t4LTZRmzQCC8Tm"
        this.mConfig.protocol = ACRCloudConfig.ACRCloudNetworkProtocol.PROTOCOL_HTTP // PROTOCOL_HTTPS
        this.mConfig.reqMode = ACRCloudConfig.ACRCloudRecMode.REC_MODE_REMOTE

    }

    fun addConfigToClient(){

        Log.d("DiscoverFragment", "addConfigToClient called")


        this.initState = this.mClient.initWithConfig(this.mConfig)

        if(this.initState)
        {
            this.mClient.startPreRecord(3000)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var rootView = inflater.inflate(R.layout.fragment_discover, container, false)

        rootView.discoverStartIdentifyButton.setOnClickListener {
            mPresenter.onStartIdentifyButtonClicked()
        }

        return rootView
    }



    // Called to start identifying/discovering the song that is currently playing
    fun startIdentification(callback: SongIdentificationCallback)
    {

        Log.d("DiscoverFragment", "startIdentification called")

        if(!initState)
        {
            Log.d("AcrCloudImplementation", "init error")
        }
        if(!mProcessing) {

            mProcessing = true
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
        if(mProcessing)
        {
            mClient.stopRecordToRecognize()
        }

        mProcessing = false
    }

    fun cancelListeningToIdentifySong()
    {
        if(mProcessing)
        {
            mProcessing = false
            mClient.cancel()
        }
    }


    override fun onResult(resultString: String?) {

        Log.d("DiscoverFragment", "onResult called")
        Log.d("DiscoverFragment",resultString)

        mClient.cancel()
        mProcessing = false

        val result = Gson().fromJson(resultString, ResponseClasses.SongIdentificationResult:: class.java)

        Log.d("DiscoverFragment", "parsed result " + result)

        if(result.status.code == 3000)
        {
            onOfflineError()
        }
        else if(result.status.code == 1001)
        {
            onSongNotFound()
        }
        else if(result.status.code == 0 )
        {
            onSongFound(MusicDataMapper().convertFromDataModel(result))
        }
        else
        {
            onGenericError()
        }


    }

    override fun onVolumeChanged(p0: Double) {

    }

    override fun startSongIdentifyService() {

        setUpConfig()
        addConfigToClient()

        startIdentification(this@DiscoverFragment)

    }

    override fun stopSongIdentifyService() {

      stopIdentification()
    }

    override fun onPause() {
        super.onPause()

        mPresenter.dropView()
    }

    override fun onOfflineError() {

    }

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

    override fun onSongNotFound() {
        hideStartIdentifyButtonView()
        showStopIdentifyButtonView()
        showIdentifyProgressView()
        hideErrorViews()
        showIdentifyProgressView()

        showNotFoundErrorView()
    }

    override fun onSongFound(song: Song) {
        hideStartIdentifyButtonView()
        showStopIdentifyButtonView()
        showIdentifyProgressView()
        hideErrorViews()
        openSongDetailPage(song)
    }

}

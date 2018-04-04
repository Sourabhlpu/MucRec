package com.example.personal.shazamclone.data.identify

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.acrcloud.rec.sdk.ACRCloudClient
import com.acrcloud.rec.sdk.ACRCloudConfig
import com.acrcloud.rec.sdk.IACRCloudListener
import com.example.personal.shazamclone.data.identify.ResponseClasses.SongIdentificationResult
import com.example.personal.shazamclone.discover.DiscoverPresenter
import com.example.personal.shazamclone.domain.MusicDataMapper
import com.example.personal.shazamclone.domain.Song
import com.google.gson.Gson

/**
 * Created by personal on 3/29/2018.
 */

class SongIdentifyService(discoverPresenter : DiscoverPresenter) : IACRCloudListener , IntentService("SongIdentifyService") {

    private val callback : SongIdentificationCallback = discoverPresenter
    private val mClient : ACRCloudClient by lazy(LazyThreadSafetyMode.NONE) { ACRCloudClient() }
    private val mConfig : ACRCloudConfig by lazy(LazyThreadSafetyMode.NONE) { ACRCloudConfig() }
    private var initState : Boolean = false
    private var mProcessing : Boolean = false


    override fun onHandleIntent(intent: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        setUpConfig()
        addConfigToClient()

        startIdentification(callback)

    }


    public fun setUpConfig(){

        mConfig.acrcloudListener = this@SongIdentifyService

        mConfig.host = "identify-eu-west-1.acrcloud.com"
        mConfig.accessKey = "ff98c0119a6fc307cde6e3708b6eeac6"
        mConfig.accessSecret = "fC1U4vP1eT5X24dIqrmnUB9px1t4LTZRmzQCC8Tm"
        mConfig.protocol = ACRCloudConfig.ACRCloudNetworkProtocol.PROTOCOL_HTTP // PROTOCOL_HTTPS
        mConfig.reqMode = ACRCloudConfig.ACRCloudRecMode.REC_MODE_REMOTE

    }

    // Called to start identifying/discovering the song that is currently playing
    fun startIdentification(callback: SongIdentificationCallback)
    {
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
        if(mProcessing)
        {
            mClient.stopRecordToRecognize()
        }

        mProcessing = false
    }

    public fun cancelListeningToIdentifySong()
    {
        if(mProcessing)
        {
            mProcessing = false
            mClient.cancel()
        }
    }

    public fun addConfigToClient(){

        initState = mClient.initWithConfig(mConfig)

        if(initState)
        {
            mClient.startPreRecord(3000)
        }
    }


    override fun onResult(result: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        mClient.cancel()
        mProcessing = false

        val result = Gson().fromJson(result, SongIdentificationResult :: class.java)

        if(result.status.code == 3000)
        {
            callback.onOfflineError()
        }
        else if(result.status.code == 1001)
        {
            callback.onSongNotFound()
        }
        else if(result.status.code == 0 )
        {
            callback.onSongFound(MusicDataMapper().convertFromDataModel(result))
        }
        else
        {
            callback.onGenericError()
        }


    }

    override fun onVolumeChanged(p0: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    interface SongIdentificationCallback {

        // Called when the user is offline and music identification failed
        fun onOfflineError()

        // Called when a generic error occurs and music identification failed
        fun onGenericError()

        // Called when music identification completed but couldn't identify the song
        fun onSongNotFound()

        // Called when identification completed and a matching song was found
        fun onSongFound(song: Song)

    }

}
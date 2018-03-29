package com.example.personal.shazamclone.data.identify

/**
 * Created by personal on 3/29/2018.
 */

interface SongIdentifyService {

    // Called to start identifying/discovering the song that is currently playing
    fun startIdentification(callback: SongIdentificationCallback)

    // Called to stop identifying/discovering song
    fun stopIdentification()

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
package com.example.personal.shazamclone.data.identify

import com.example.personal.shazamclone.domain.Song

/**
 * Created by personal on 3/29/2018.
 */
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


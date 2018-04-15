package com.example.personal.shazamclone.domain

import com.example.personal.shazamclone.data.identify.ResponseClasses.Artists
import com.example.personal.shazamclone.data.identify.ResponseClasses.SongIdentificationResult

/**
 * Created by personal on 4/4/2018.
 */

class MusicDataMapper{


    fun convertFromDataModel(songIdentificationResult : SongIdentificationResult,
                             vidId : String? = null):Song {


        val title = songIdentificationResult.metadata.music[0].title
        val artists = convertArtistsToString(songIdentificationResult.metadata.music[0].artists)
        val albumName = songIdentificationResult.metadata.music[0].album.name
        var videoId : String? = vidId

        if(videoId == null)
        {
            videoId = songIdentificationResult.metadata.music.get(0).external_metadata.youtube.vid
        }
        return Song(title,artists,albumName, videoId!!)


    }

    fun convertArtistsToString(artists : List<Artists>) : String {

        return artists.map { it.name }.joinToString (",","", "", 3,
                "...")
    }

}
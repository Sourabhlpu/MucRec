package com.example.personal.shazamclone.domain

import com.example.personal.shazamclone.data.identify.ResponseClasses.Artists
import com.example.personal.shazamclone.data.identify.ResponseClasses.SongIdentificationResult

/**
 * Created by personal on 4/4/2018.
 */

class MusicDataMapper{

    fun convertFromDataModel(songIdentificationResult : SongIdentificationResult) : Song {

        return Song(songIdentificationResult.metadata.music.get(0).title,
                convertArtistsToString(songIdentificationResult.metadata.music[0].artists),
                songIdentificationResult.metadata.music.get(0).album.name)
    }

    fun convertArtistsToString(artists : List<Artists>) : String {

        return artists.map { it.name }.joinToString (",","", "", 3,
                "...")
    }

}
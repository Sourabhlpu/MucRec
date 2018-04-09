package com.example.personal.shazamclone.domain

import com.example.personal.shazamclone.data.identify.ResponseClasses.SongIdentificationResult

/**
 * Created by personal on 4/4/2018.
 */

class MusicDataMapper{

    fun convertFromDataModel(songIdentificationResult : SongIdentificationResult) : Song {

        return Song(songIdentificationResult.metadata.music[0].title,
                songIdentificationResult.metadata.music[0].artists.joinToString(
                        ",", "", "", -1, "...", null
                ),
                songIdentificationResult.metadata.music[0].album.name)
    }

}
package com.example.personal.shazamclone.domain

import com.example.personal.shazamclone.data.identify.ResponseClasses.SongIdentificationResult

/**
 * Created by personal on 4/4/2018.
 */

class MusicDataMapper{

    fun convertFromDataModel(songIdentificationResult : SongIdentificationResult) : Song {

        return Song(songIdentificationResult.metaData.music.get(0).title,
                songIdentificationResult.metaData.music.get(0).artists.joinToString(
                        ",", "", "", -1, "...", null
                ),
                songIdentificationResult.metaData.music.get(0).album.name)
    }

}
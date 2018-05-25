package com.example.personal.shazamclone.domain

import android.util.Log
import com.example.personal.shazamclone.data.identify.ResponseClasses.Artists
import com.example.personal.shazamclone.data.identify.ResponseClasses.SongIdentificationResult
import com.example.personal.shazamclone.data.identify.db.room.SongEntity

/**
 * Created by personal on 4/4/2018.
 */

class MusicDataMapper{


      // this function converts a song object into an SongEntity so that it can be saved into the database
        fun convertSongToEntity(song : Song) : SongEntity {

            Log.d("MusicDataMapper", "converting song to entity ${song.name}")

            val songEntity = SongEntity(song.name, song.artist, song.album, song.youtubeLink)

            Log.d("MusicDataMapper", " song name from entity ${songEntity.name}")

            return songEntity
        }

      /*  fun convertEntityToSong(song : SongEntity) : Song
        {
            return Song(song.name, song.artist, song.album,song.vidId)
        }*/



    // this method converts the identification result into a song object
    fun convertFromDataModel(songIdentificationResult : SongIdentificationResult,
                             vidId : String? = null):Song {


        val title = songIdentificationResult.metadata.music[0].title
        val artists = convertArtistsToString(songIdentificationResult.metadata.music[0].artists)
        val albumName = songIdentificationResult.metadata.music[0].album.name
        val isrc = songIdentificationResult.metadata.music[0].external_ids.isrc
        var videoId : String? = vidId

        if(videoId == null)
        {
            videoId = songIdentificationResult.metadata.music.get(0).external_metadata.youtube.vid
        }
        return Song(title,artists,albumName, videoId!!, isrc)


    }

    fun convertArtistsToString(artists : List<Artists>) : String {

        return artists.map { it.name }.joinToString (",","", "", 3,
                "...")
    }



}
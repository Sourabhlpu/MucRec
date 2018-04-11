package com.example.personal.shazamclone.data.identify

/**
 * Created by personal on 4/3/2018.
 */
class ResponseClasses {

    data class SongIdentificationResult(val status: Status, val metadata: MetaData, val result_type: Int)

    data class Status(val msg: String, val code: Int, val version: String)

    data class MetaData(val played_duration: Int, val music: List<Music>, val timestamp_utc: String)

    data class Music(val external_ids: ExternalIds, val sample_begin_time_offset_ms: String,
                     val label: String, val external_metadata: ExternalMetadata,
                     val play_offset_ms: Int, val artists: List<Artists>,
                     val sample_end_time_offset_ms: String, val release_date: String,
                     val title: String, val db_end_time_offset_ms: String, val duration_ms: String,
                     val album: Album, val acric: String, val result_from: Int,
                     val db_begin_time_offset_ms: String, val scrore: Int)

    data class ExternalIds(val isrc: String, val upc: String)

    data class ExternalMetadata(val spotify : Spotify)

    data class Spotify(val album : SpotifyAlbum, val artists : List<SpotifyArtists>, val track : Track)

    data class SpotifyAlbum(val id : String)

    data class SpotifyArtists(val id : String)

    data class Track(val id : String)

    data class Album(val name : String)

    data class Artists(val name : String)
}

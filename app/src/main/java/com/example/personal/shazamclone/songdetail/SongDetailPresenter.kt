package com.example.personal.shazamclone.songdetail

import com.example.personal.shazamclone.data.identify.db.ShazamRepository

/**
 * Created by personal on 4/27/2018.
 */
class SongDetailPresenter : SongDetailContract.Presenter {



    private lateinit var mSongDetailView : SongDetailContract.View

    override fun takeView(view: SongDetailContract.View) {

        mSongDetailView = view

        mSongDetailView.setPresenter(this)
    }

    override fun dropView() {

    }

    override fun updateImageUrl(url: String, yLink: String) {

        ShazamRepository.instance.updateImageUrl(url, yLink)
    }



    override fun getCoverArtUrl(isrc: String, track: String, artist: String) : String =

            ShazamRepository.instance.getCoverArtUrl(isrc,track,artist)

}
package com.example.personal.shazamclone.songdetail

import com.example.personal.shazamclone.data.identify.db.ShazamRepository

/**
 * Created by personal on 4/27/2018.
 * This presenter is responsible for intereacting with the repository and acting as a bridge between
 * the view and the model. As usual the presenter will extend the Contract's presenter interface
 */
class SongDetailPresenter : SongDetailContract.Presenter {

    // a reference to the View so that we can call methods in the fragment
    private lateinit var mSongDetailView : SongDetailContract.View

    // override this method to initialize the View reference and also set the presenter for the View
    override fun takeView(view: SongDetailContract.View) {

        mSongDetailView = view

        mSongDetailView.setPresenter(this)
    }

    override fun dropView() {

    }

    // this method will intreact with  the repository to update the image url of the song that was already
    // saved
    override fun updateImageUrl(url: String, yLink: String) {

        ShazamRepository.instance.updateImageUrl(url, yLink)
    }



    // this method will intreact with the repository to get the cover art for the track
    override fun getCoverArtUrl(isrc: String, track: String, artist: String) : String =

            ShazamRepository.instance.getCoverArtUrl(isrc,track,artist)

}
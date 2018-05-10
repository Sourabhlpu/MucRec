package com.example.personal.shazamclone.songdetail

import com.example.personal.shazamclone.BasePresenter
import com.example.personal.shazamclone.BaseView

/**
 * Created by personal on 4/27/2018.
 */

interface SongDetailContract {

    interface View : BaseView<Presenter> {


    }

    interface  Presenter : BasePresenter<View> {

        fun updateImageUrl(url: String, yLink: String)


        fun getCoverArtUrl(isrc : String, track: String, artist: String) : String
    }
}
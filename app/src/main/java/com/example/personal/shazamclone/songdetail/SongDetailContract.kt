package com.example.personal.shazamclone.songdetail

import com.example.personal.shazamclone.BasePresenter
import com.example.personal.shazamclone.BaseView

/**
 * Created by personal on 4/27/2018.
 * This is the contract defined between the SongDetailFragment and the SongDetailPresenter
 * As usual it is an interface which defines all the methods that will be implemented by the
 * presenter and the fragment
 */

interface SongDetailContract {

    // An interface that will be implemented by the fragment. It extend from the BaseView, which has
    // a method setPresenter. Set presenter takes a generic of the the type Presenter

    interface View : BaseView<Presenter> {

        // right now there are no methods that needs to be defined here
    }

    // another interface that will be implemented by the presenter. It extend the BasePresenter and
    // take View as the generic parameter
    interface  Presenter : BasePresenter<View> {

        // this method will be implemented by the presenter
        fun updateImageUrl(url: String, yLink: String)


        fun getCoverArtUrl(isrc : String, track: String, artist: String) : String
    }
}
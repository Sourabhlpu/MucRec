package com.example.personal.shazamclone

/**
 * Created by personal on 3/26/2018.
 */
interface BasePresenter<in V> {

    fun takeView(view : V)


    fun dropView()
}
package com.example.personal.shazamclone

/**
 * Created by personal on 3/26/2018.
 * Every Presenter interface in the contract should implement this.
 */
interface BasePresenter<in V> {

    fun takeView(view : V)


    fun dropView()
}
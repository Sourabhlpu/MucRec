package com.example.personal.shazamclone

/**
 * Created by personal on 3/26/2018.
 *
 * Every View interface in the contract should implement this
 */
interface BaseView<P> {

    fun setPresenter(presenter : P)
}
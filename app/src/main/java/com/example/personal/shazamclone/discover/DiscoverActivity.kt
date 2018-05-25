package com.example.personal.shazamclone.discover

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.utils.FragmentUtils

class DiscoverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_discover)

        // initializing the discover fragment that will be attached to this activity
        val discoverFragment: DiscoverFragment = DiscoverFragment()

        // using the utils class to add the fragment to the activity
        FragmentUtils.addIfNotExists(supportFragmentManager, R.id.discoverFragmentContainer,
                discoverFragment, "DiscoverFragment")

        // here we initialize the presenter and then call the method on it to pass the view
        // this will help the presenter to call the methods in the fragment
        // Remember takeView takes the argument of the type DiscoverContract.View and since discover
        // fragment implements it, it works here
        DiscoverPresenter().takeView(discoverFragment)


    }


}

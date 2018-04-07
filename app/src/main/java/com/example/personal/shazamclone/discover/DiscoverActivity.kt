package com.example.personal.shazamclone.discover

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.utils.FragmentUtils

class DiscoverActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)


        val discoverFragment : DiscoverFragment = DiscoverFragment()

        FragmentUtils.addIfNotExists(supportFragmentManager,R.id.discoverFragmentContainer,
                discoverFragment, "DiscoverFragment")

        DiscoverPresenter().takeView(discoverFragment)
    }
}

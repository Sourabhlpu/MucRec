package com.example.personal.shazamclone.discover

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.personal.shazamclone.IntroActivity
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.utils.FragmentUtils
import com.example.personal.shazamclone.utils.PrefManager

class DiscoverActivity : AppCompatActivity() {

    private val REQUEST_PERMISSIONS_CODE = 100;


    private val pref : PrefManager by lazy { PrefManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {


        if(pref.isFirstTimeLaunch)
        {

            launchOnboarding()
        }

        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)


        val discoverFragment : DiscoverFragment = DiscoverFragment()

        FragmentUtils.addIfNotExists(supportFragmentManager,R.id.discoverFragmentContainer,
                discoverFragment, "DiscoverFragment")

        DiscoverPresenter().takeView(discoverFragment)

    }

    private fun launchOnboarding()
    {
        val intent : Intent = Intent(DiscoverActivity@this, IntroActivity :: class.java)
        startActivity(intent)
    }

}

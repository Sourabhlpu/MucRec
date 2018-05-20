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


    private val pref: PrefManager by lazy { PrefManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (pref.isFirstTimeLaunch) {

            launchOnboarding()
        }
        else if(!pref.isFirstTimeLaunch) {


            setTheme(R.style.AppTheme)

            setContentView(R.layout.activity_discover)


            val discoverFragment: DiscoverFragment = DiscoverFragment()

            FragmentUtils.addIfNotExists(supportFragmentManager, R.id.discoverFragmentContainer,
                    discoverFragment, "DiscoverFragment")

            DiscoverPresenter().takeView(discoverFragment)
        }


    }

    private fun launchOnboarding() {
        val intent: Intent = Intent(DiscoverActivity@ this, IntroActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {

        if(pref.isFirstTimeLaunch){
            System.gc()
            System.exit(0)
        }
    }
}

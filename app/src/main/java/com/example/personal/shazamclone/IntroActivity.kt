package com.example.personal.shazamclone

import agency.tango.materialintroscreen.MaterialIntroActivity
import agency.tango.materialintroscreen.SlideFragmentBuilder
import android.Manifest
import android.content.Intent
import android.os.Bundle
import com.example.personal.shazamclone.discover.DiscoverActivity
import com.example.personal.shazamclone.utils.PrefManager


/**
 * Created by personal on 5/5/2018.
 */
class IntroActivity : MaterialIntroActivity() {

    private val pref : PrefManager by lazy { PrefManager(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashTheme)
        super.onCreate(savedInstanceState)

        if(!pref.isFirstTimeLaunch)
        {
            launchDiscoverActivity()
        }
        else {

            addSlide(SlideFragmentBuilder()
                    .backgroundColor(R.color.first_slide_background)
                    .buttonsColor(R.color.first_slide_buttons)
                    .image(R.drawable.search)
                    .title(getString(R.string.slide_one_title))
                    .description(getString(R.string.slide_one_description))
                    .build())

            addSlide(SlideFragmentBuilder()
                    .backgroundColor(R.color.second_slide_background)
                    .buttonsColor(R.color.second_slide_buttons)
                    .image(R.drawable.save)
                    .title(getString(R.string.slide_two_title))
                    .neededPermissions(arrayOf(Manifest.permission.RECORD_AUDIO))
                    .build())

            addSlide(SlideFragmentBuilder()
                    .backgroundColor(R.color.third_slide_background)
                    .buttonsColor(R.color.third_slide_buttons)
                    .image(R.drawable.play_video)
                    .title(getString(R.string.slide_three_title))
                    .description(getString(R.string.slide_three_description))
                    .build())

        }

    }

    override fun onFinish() {
        pref.isFirstTimeLaunch = false
        launchDiscoverActivity()
    }

    private fun launchDiscoverActivity()
    {
        val intent = Intent(IntroActivity@this, DiscoverActivity :: class.java)
        startActivity(intent)
        finish()
    }
}
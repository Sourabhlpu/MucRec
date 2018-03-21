package com.example.personal.shazamclone

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.personal.shazamclone.discover.DiscoverActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by personal on 3/21/2018.
 */


@RunWith(AndroidJUnit4 :: class)

@LargeTest
class DiscoverActivityTest{

    @JvmField
    @Rule
    val mDiscoverActivityTestRule = ActivityTestRule<DiscoverActivity>(DiscoverActivity::class.java)

    @Test
    fun onViewLoadedShowDiscoverFragment(){

        onView(withId(R.id.discoverFragmentView)).check(matches(isDisplayed()))
    }
}
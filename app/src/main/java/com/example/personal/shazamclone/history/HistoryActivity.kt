package com.example.personal.shazamclone.history

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.toolbar.view.*

/*
 * This activity shows the list of all the songs that were discovered before
 */

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        //initialize the history fragment
        val historyFragment: HistoryFragment = HistoryFragment()

        // set the custom toolbar
        setSupportActionBar(history_toolbar.toolbar)


        // enable the back button in the acton bar to act as a home button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        // disabling the default text of the action bar
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        //setting the action bar name
        history_toolbar.toolbar_title.text = getString(R.string.history_title)


        // now just using the fragments utils class to attach the fragment to the activity
        FragmentUtils.addIfNotExists(supportFragmentManager,R.id.historyFragmentContainer,
                historyFragment, "HistoryFragment")

        // like usual create an object of the presenter and passing in the fragment reference to the
        // takeView method.
        HistoryPresenter().takeView(historyFragment)
    }
}

package com.example.personal.shazamclone.history

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.toolbar.view.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val historyFragment: HistoryFragment = HistoryFragment()

        setSupportActionBar(history_toolbar.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        history_toolbar.toolbar_title.text = getString(R.string.history_title)

        FragmentUtils.addIfNotExists(supportFragmentManager,R.id.historyFragmentContainer,
                historyFragment, "HistoryFragment")

        HistoryPresenter().takeView(historyFragment)
    }
}

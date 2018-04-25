package com.example.personal.shazamclone.history

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.utils.FragmentUtils

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val historyFragment: HistoryFragment = HistoryFragment()

        FragmentUtils.addIfNotExists(supportFragmentManager,R.id.historyFragmentContainer,
                historyFragment, "HistoryFragment")

        HistoryPresenter().takeView(historyFragment)
    }
}

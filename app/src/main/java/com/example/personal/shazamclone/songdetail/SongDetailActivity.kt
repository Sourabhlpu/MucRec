package com.example.personal.shazamclone.songdetail

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_song_detail.*

class SongDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)

        setSupportActionBar(song_detail_toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val songDetailFragment : SongDetailFragment = SongDetailFragment()

        FragmentUtils.addIfNotExists(supportFragmentManager, R.id.songDetailFragmentContainer,
                songDetailFragment, "SongDetailFragment")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId)
        {
            android.R.id.home -> NavUtils.navigateUpFromSameTask(this)

        }
        return super.onOptionsItemSelected(item)
    }
}

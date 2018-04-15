package com.example.personal.shazamclone.songdetail

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.utils.FragmentUtils
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
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


        setUpYoutubePlayer()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId)
        {
            android.R.id.home -> NavUtils.navigateUpFromSameTask(this)

        }
        return super.onOptionsItemSelected(item)
    }

    fun setUpYoutubePlayer()
    {
        val youtubeFragment = fragmentManager.findFragmentById(R.id.songYoutubeFragment)
                as YouTubePlayerFragment

        youtubeFragment.initialize(getString(R.string.youtube_api_key),
                object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(provider: YouTubePlayer.Provider,
                                                         youTubePlayer: YouTubePlayer, b: Boolean) {
                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(intent.getStringExtra(getString(R.string.song_youtube_id)))

                    }

                    override fun onInitializationFailure(provider: YouTubePlayer.Provider,
                                                         youTubeInitializationResult: YouTubeInitializationResult) {

                    }
                })

    }
}

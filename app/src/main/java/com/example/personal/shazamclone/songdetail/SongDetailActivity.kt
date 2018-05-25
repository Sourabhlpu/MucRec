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

        //setting up the custom toolbar
        setSupportActionBar(song_detail_toolbar)

        //making the back button in the toolbar go to the parent activity when clicked
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //removing the title from the action bar
        supportActionBar!!.setDisplayShowTitleEnabled(false)


        //creating an instance of the fragment
        val songDetailFragment : SongDetailFragment = SongDetailFragment()

        //uisng the FragmentUitls to add the fragment to the activity
        FragmentUtils.addIfNotExists(supportFragmentManager, R.id.songDetailFragmentContainer,
                songDetailFragment, "SongDetailFragment")


        //initializing the presenter and calling the takeView method to pass the reference to
        // the fragment so that the presenter can call the methods defined in the contracts's View
        // interface, implemented by the fragment. takeView will also call the setPresenter method
        // passing in the reference to the presenter itself and hence enabling the fragment to call
        // the methods in overriden in the presenter.
        SongDetailPresenter().takeView(songDetailFragment)

        setUpYoutubePlayer()



    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId)
        {
            // make the back button at the bottom to go to the parent activity i.e. Discover activity
            android.R.id.home -> NavUtils.navigateUpFromSameTask(this)

        }
        return super.onOptionsItemSelected(item)
    }

    // this function creates the youtube player so that we can play the video in the song details
    // page
    fun setUpYoutubePlayer()
    {
        //find the youtube fragment
        val youtubeFragment = fragmentManager.findFragmentById(R.id.songYoutubeFragment)
                as YouTubePlayerFragment

        // initializing the youtube player with the api key
        youtubeFragment.initialize(getString(R.string.youtube_api_key),
                object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(provider: YouTubePlayer.Provider,
                                                         youTubePlayer: YouTubePlayer, b: Boolean) {
                        // do any work here to cue video, play video, etc.
                        // we have the youtube id passed in the intent so we get the intent extra
                        // for the vid id
                        youTubePlayer.cueVideo(intent.getStringExtra(getString(R.string.song_youtube_id)))

                    }

                    override fun onInitializationFailure(provider: YouTubePlayer.Provider,
                                                         youTubeInitializationResult: YouTubeInitializationResult) {

                    }
                })

    }
}

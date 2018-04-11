package com.example.personal.shazamclone.songdetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.personal.shazamclone.R
import kotlinx.android.synthetic.main.fragment_song_details.view.*

/**
 * Created by personal on 4/10/2018.
 */
 class SongDetailFragment : Fragment()
{

 override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

  var rootView = inflater.inflate(R.layout.fragment_song_details, container, false)

  rootView.song_name.text = activity!!.intent.extras.getString(getString(R.string.song_name_extra))

  rootView.artist_name.text =  activity!!.intent.extras.getString(getString(R.string.song_artist_extra))

  rootView.album_name.text = activity!!.intent.extras.getString(getString(R.string.song_album_extra))


  return rootView
 }
}
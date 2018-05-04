package com.example.personal.shazamclone.songdetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.AsyncTaskLoader
import android.support.v4.content.Loader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.personal.shazamclone.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_song_details.*
import kotlinx.android.synthetic.main.fragment_song_details.view.*

/**
 * Created by personal on 4/10/2018.
 */
 class SongDetailFragment : Fragment(), SongDetailContract.View, LoaderManager.LoaderCallbacks<String>
{



 val isrc by lazy { activity!!.intent.getStringExtra(getString(R.string.isrc_id_extra)) }

 val track by lazy { activity!!.intent.getStringExtra(getString(R.string.song_name_extra)) }

 val artist by lazy { activity!!.intent.getStringExtra(getString(R.string.song_artist_extra)) }


 private lateinit var mPresenter : SongDetailContract.Presenter

 private val LOADER_ID = 102


 override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

  var rootView = inflater.inflate(R.layout.fragment_song_details, container, false)

  rootView.song_name.text = track

  rootView.artist_name.text =  artist

  rootView.album_name.text = activity!!.intent.extras.getString(getString(R.string.song_album_extra))

  loaderManager.initLoader(LOADER_ID, null, this)


  return rootView
 }


 override fun setPresenter(presenter: SongDetailContract.Presenter) {

  mPresenter = presenter
 }



 override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
  return object : AsyncTaskLoader<String>(activity!!.baseContext){

   override fun onStartLoading() {

    forceLoad()
   }

   override fun loadInBackground(): String? {

    return mPresenter.getImageUrl(isrc, track, artist)

   }

  }
 }

 override fun onLoadFinished(loader: Loader<String>?, data: String?) {

  Log.d("SongDetailFragment", "the url is $data")

  if(data.equals("")){

   Picasso.get().load(R.drawable.album_art_placeholder).into(album_art)
  }

  else {
   Picasso.get().load(data)
           .error(R.drawable.album_art_placeholder)
           .placeholder(R.drawable.album_art_placeholder)
           .into(album_art)
  }
 }

 override fun onLoaderReset(loader: Loader<String>?) {

 }
}
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

 // stores the isrc of the track which we get from the intent extras
 val isrc by lazy { activity!!.intent.getStringExtra(getString(R.string.isrc_id_extra)) }

 // get the track name from the intent extras
 val track by lazy { activity!!.intent.getStringExtra(getString(R.string.song_name_extra)) }

 // get the artist name from the intent extras
 val artist by lazy { activity!!.intent.getStringExtra(getString(R.string.song_artist_extra)) }

 // get the vidId from the intent extras
 val youtubeLink by lazy { activity!!.intent.getStringExtra(getString(R.string.song_youtube_id))}


 // a reference to the presenter so that we can call the methods in the presenter
 private lateinit var mPresenter : SongDetailContract.Presenter

 // a id for the loader
 private val LOADER_ID = 102


 override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

  var rootView = inflater.inflate(R.layout.fragment_song_details, container, false)

  // setting the views in the fragment
  rootView.song_name.text = track

  rootView.artist_name.text =  artist

  rootView.album_name.text = activity!!.intent.extras.getString(getString(R.string.song_album_extra))

  //starting the loader
  loaderManager.initLoader(LOADER_ID, null, this)


  return rootView
 }


 //overriding the method to set the presenter
 override fun setPresenter(presenter: SongDetailContract.Presenter) {

  mPresenter = presenter
 }



 override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
  return object : AsyncTaskLoader<String>(activity!!.baseContext){

   override fun onStartLoading() {

    forceLoad()
   }

   override fun loadInBackground(): String? {


    if (isrc.isNullOrEmpty()) return ""
   // we are calling the method in the presenter to get the album art for the track. We pass the
    // isrc, track and artist name. Most of the time only isrc will be enough but sometimes isrc can
    // be null as wel
    return mPresenter.getCoverArtUrl(isrc, track, artist)

   }

  }
 }

 override fun onLoadFinished(loader: Loader<String>?, data: String?) {

  Log.d("SongDetailFragment", "the url is $data")

  if(data.isNullOrEmpty()){

   // if the url is null just load the saved image locally
   Picasso.get().load(R.drawable.album_art_placeholder).into(album_art)
  }

  else {
   Picasso.get().load(data)
           .error(R.drawable.album_art_placeholder)
           .placeholder(R.drawable.album_art_placeholder)
           .into(album_art)

   // we got the url so we also need to update that in the database. Calling the method on the
   // presenter so that we can update the database
   mPresenter.updateImageUrl(data!!, youtubeLink)
  }
 }

 override fun onLoaderReset(loader: Loader<String>?) {

 }
}
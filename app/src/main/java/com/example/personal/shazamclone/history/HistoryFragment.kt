package com.example.personal.shazamclone.history

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.AsyncTaskLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.data.identify.db.room.SongEntity
import kotlinx.android.synthetic.main.fragment_song_history.*



/**
 * Created by personal on 4/23/2018.
 * this fragment will display all the history of songs identified
 * Will be using loader here to fetch all the songs and then updating the adapter
 */
class HistoryFragment : Fragment(), HistoryContract.view,
        LoaderManager.LoaderCallbacks<List<SongEntity>>{


    // variable to hold the reference to the Presenter
    private lateinit var mPresenter : HistoryContract.Presenter

    // variable to hold the reference to the adapter
    private lateinit var adapter: HistoryAdapter

    // and id for the loader
    private val loaderId : Int = 101



    override fun onLoadFinished(loader: Loader<List<SongEntity>>?, data: List<SongEntity>?) {

        Log.d("HistoryFragment", "onLoadFinished called")

        // loading completed so hide the loading indicator
        hideLoadingIndicator()

        // check if the data is valid
        if(data == null || data.size < 1 )
        {
            // show the error if not
            showErrorView()
        }
        else
        {
            // we got the data so we need to update the adapter

            Log.d("HistoryFragment", "${data[0].name}, ${data[0].album}, the size of the" +
                    "list is ${data.size}")
            adapter.updateDataset(data)
        }

    }

    override fun onLoaderReset(loader: Loader<List<SongEntity>>?) {

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<SongEntity>> {

        Log.d("HistoryFragment", "onCreateLoader called")

       showLoadingIndicator()

        return object : AsyncTaskLoader<List<SongEntity>>(activity!!.baseContext){

            override fun onStartLoading() {

                showLoadingIndicator()

                forceLoad()
            }

            override fun loadInBackground(): List<SongEntity>? {

                // calling a method on the presenter to fetch a list of songs saved in the database
                return mPresenter.fetchSongs()

            }


        }

    }


    // method to show the loading indicator
    override fun showLoadingIndicator() {

        Log.d("HistoryFragment", "showing loading indicator")

        song_history_pv.visibility = View.VISIBLE
    }

    // method to hide the loading indicator
    override fun hideLoadingIndicator() {

        Log.d("HistoryFragment", "hiding loading indicator")

        song_history_pv.visibility = View.INVISIBLE
    }

    // method to show the error view
    override fun showErrorView() {

        Log.d("HistoryFragment", "showing error view")

        song_history_error_tv.visibility = View.VISIBLE
    }

    // method to hide the error view
    override fun hideErrorView() {

        Log.d("HistoryFragment", "hiding error view")

        song_history_error_tv.visibility = View.INVISIBLE
    }

    // this method will be called in the Presenter's takeView method to set the reference to the presenter
    override fun setPresenter(presenter: HistoryContract.Presenter) {

        mPresenter = presenter

    }

    override fun showDiscoveredSongs(songs: List<SongEntity>) {


    }


    // we inflate the layout there and then just return the rootView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        val rootView = inflater.inflate(R.layout.fragment_song_history, container, false)


        return rootView
    }

    // we do all the setting up the the layout here as in onViewCreated the view is done creating and
    // we don't get any null pointer exception due to view still not created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // start the loader
        loaderManager.initLoader(loaderId, null , this)

        // setting the layout manager for the recycler view
        songHistoryRv.layoutManager = LinearLayoutManager(context)

        // initializing the adapter object
        adapter = HistoryAdapter(mutableListOf<SongEntity>()){

            Toast.makeText(activity!!.baseContext, "${it.name}", Toast.LENGTH_SHORT).show()

        }

        //setting the adapter for the recyclerView
        songHistoryRv.adapter = adapter


    }
}
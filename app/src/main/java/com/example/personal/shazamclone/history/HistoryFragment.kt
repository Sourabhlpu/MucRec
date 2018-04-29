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
 */
class HistoryFragment : Fragment(), HistoryContract.view,
        LoaderManager.LoaderCallbacks<List<SongEntity>>{


    private lateinit var mPresenter : HistoryContract.Presenter

    private lateinit var adapter: HistoryAdapter

    private val loaderId : Int = 101



    override fun onLoadFinished(loader: Loader<List<SongEntity>>?, data: List<SongEntity>?) {

        Log.d("HistoryFragment", "onLoadFinished called")

        hideLoadingIndicator()

        if(data == null || data.size < 1 )
        {
            showErrorView()
        }
        else
        {

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

                return mPresenter.fetchSongs()

            }


        }

    }


    override fun showLoadingIndicator() {

        Log.d("HistoryFragment", "showing loading indicator")

        song_history_pv.visibility = View.VISIBLE
    }

    override fun hideLoadingIndicator() {

        Log.d("HistoryFragment", "hiding loading indicator")

        song_history_pv.visibility = View.INVISIBLE
    }

    override fun showErrorView() {

        Log.d("HistoryFragment", "showing error view")

        song_history_error_tv.visibility = View.VISIBLE
    }

    override fun hideErrorView() {

        Log.d("HistoryFragment", "hiding error view")

        song_history_error_tv.visibility = View.INVISIBLE
    }

    override fun setPresenter(presenter: HistoryContract.Presenter) {

        mPresenter = presenter

    }

    override fun showDiscoveredSongs(songs: List<SongEntity>) {


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        val rootView = inflater.inflate(R.layout.fragment_song_history, container, false)


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loaderManager.initLoader(loaderId, null , this)

        songHistoryRv.layoutManager = LinearLayoutManager(context)

        adapter = HistoryAdapter(mutableListOf<SongEntity>()){

            Toast.makeText(activity!!.baseContext, "${it.name}", Toast.LENGTH_SHORT).show()

        }

        songHistoryRv.adapter = adapter


    }
}
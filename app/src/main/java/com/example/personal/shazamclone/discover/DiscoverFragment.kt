package com.example.personal.shazamclone.discover


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.data.identify.SongIdentifyService
import com.example.personal.shazamclone.domain.Song
import com.example.personal.shazamclone.history.HistoryActivity
import com.example.personal.shazamclone.songdetail.SongDetailActivity
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.fragment_discover.view.*


/**
 * A simple [Fragment] subclass.
 */
open class DiscoverFragment : Fragment(),DiscoverContract.View {

    private lateinit var mPresenter : DiscoverContract.Presenter


    override fun setPresenter(presenter: DiscoverContract.Presenter) {

        mPresenter = presenter
    }


    override fun showIdentifyProgressView() {

        discoverIdentifyProgressView.visibility = View.VISIBLE
    }

    override public fun hideIdentifyProgressView() {

        discoverIdentifyProgressView.visibility = View.GONE
    }

    override fun showStartIdentifyButtonView() {

        discoverStartIdentifyButton.visibility = View.VISIBLE
    }

    override fun hideStartIdentifyButtonView() {

        discoverStartIdentifyButton.visibility = View.GONE
    }

    override fun showStopIdentifyButtonView() {

        discoverStopIdentifyButton.visibility = View.VISIBLE
    }

    override fun hideStopIdentifyButtonView() {

        discoverStopIdentifyButton.visibility = View.GONE
    }

    override fun showOfflineErrorView() {

    }

    override fun showGenericErrorView() {

    }

    override fun showNotFoundErrorView() {

    }

    override fun hideErrorViews() {
    }

    override fun openSongDetailPage(song: Song) {

        val intent = Intent(activity, SongDetailActivity::class.java)

        //have to pass the song object so as to display the song details;

        startActivity(intent)

    }

    override fun openDonatePage() {
        val intent = Intent(activity, HistoryActivity::class.java)
        startActivity(intent)
    }

    override fun openHistoryPage() {

        val intent = Intent(activity, HistoryActivity ::class.java)

        startActivity(intent)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var rootView = inflater.inflate(R.layout.fragment_discover, container, false)

        rootView.discoverStartIdentifyButton.setOnClickListener {
            mPresenter.onStartIdentifyButtonClicked()
        }

        return rootView
    }

    override fun startSongIdentifyService() {

        val intent = Intent(activity, SongIdentifyService :: class.java)

        activity!!.startService(intent)
    }

    override fun stopSongIdentifyService() {

        val intent = Intent(activity, SongIdentifyService :: class.java)

        activity!!.stopService(intent)
    }

    override fun onPause() {
        super.onPause()

        mPresenter.dropView()
    }
}// Required empty public constructor

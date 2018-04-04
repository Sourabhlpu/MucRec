package com.example.personal.shazamclone.discover


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.domain.Song
import com.example.personal.shazamclone.history.HistoryActivity
import com.example.personal.shazamclone.songdetail.SongDetailActivity
import kotlinx.android.synthetic.main.fragment_discover.*


/**
 * A simple [Fragment] subclass.
 */
class DiscoverFragment : Fragment(),DiscoverContract.View {


    override fun showIdentifyProgressView() {

        discoverIdentifyProgressView.visibility = View.VISIBLE
    }

    override fun hideIdentifyProgressView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        discoverIdentifyProgressView.visibility = View.GONE
    }

    override fun showStartIdentifyButtonView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        discoverStartIdentifyButton.visibility = View.VISIBLE
    }

    override fun hideStartIdentifyButtonView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        discoverStartIdentifyButton.visibility = View.GONE
    }

    override fun showStopIdentifyButtonView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        discoverStopIdentifyButton.visibility = View.VISIBLE
    }

    override fun hideStopIdentifyButtonView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        discoverStopIdentifyButton.visibility = View.GONE
    }

    override fun showOfflineErrorView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showGenericErrorView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNotFoundErrorView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideErrorViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openSongDetailPage(song: Song) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        val intent = Intent(activity, SongDetailActivity::class.java)

        //have to pass the song object so as to display the song details;

        startActivity(intent)

    }

    override fun openDonatePage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val intent = Intent(activity, HistoryActivity::class.java)
        startActivity(intent)
    }

    override fun openHistoryPage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val intent = Intent(activity, HistoryActivity ::class.java)

        startActivity(intent)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

}// Required empty public constructor

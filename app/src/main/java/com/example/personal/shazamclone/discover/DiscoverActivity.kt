package com.example.personal.shazamclone.discover

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.example.personal.shazamclone.R
import com.example.personal.shazamclone.utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_discover.*

class DiscoverActivity : AppCompatActivity() {

    private val REQUEST_PERMISSIONS_CODE = 100;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)


        val discoverFragment : DiscoverFragment = DiscoverFragment()

        FragmentUtils.addIfNotExists(supportFragmentManager,R.id.discoverFragmentContainer,
                discoverFragment, "DiscoverFragment")

        DiscoverPresenter().takeView(discoverFragment)

        if(!checkPermissions())
        {
            requestPermissions()
        }
    }

    fun checkPermissions() : Boolean {

        val permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)

        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions()
    {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)

        if(shouldProvideRationale)
        {
            Snackbar.make(
                    discoverFragmentContainer,
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, {

                        ActivityCompat.requestPermissions(this@DiscoverActivity,
                                arrayOf(Manifest.permission.RECORD_AUDIO),
                                REQUEST_PERMISSIONS_CODE)
                    }).show()

        }
        else
        {
            ActivityCompat.requestPermissions(this@DiscoverActivity,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    REQUEST_PERMISSIONS_CODE)
        }
    }
}

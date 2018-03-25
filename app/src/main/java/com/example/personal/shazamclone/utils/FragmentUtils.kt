package com.example.personal.shazamclone.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager


/**
 * Created by personal on 3/21/2018.
 */
object FragmentUtils {

    fun addIfNotExists(fm: FragmentManager, containerRes: Int, fragment: Fragment, tag: String )
    {
        val existingFragmentWithTag = fm.findFragmentByTag(tag);

        if(existingFragmentWithTag == null)
        {
            fm.beginTransaction().add(containerRes,fragment,tag).commit()
        }
    }
}
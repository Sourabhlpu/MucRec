package com.example.personal.shazamclone

/**
 * Created by personal on 4/22/2018.
 */
import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor;
import java.util.concurrent.Executors

class AppExecutors(val diskIO : Executor, val networkIO : Executor, val mainThread : Executor){

    companion object {

        val instance by lazy { AppExecutors(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3), MainThreadExecutor()) }
    }

    private class MainThreadExecutor : Executor {

        private val mainThreadHandler : Handler = Handler(Looper.getMainLooper())

        override fun execute(command : Runnable?) {

            mainThreadHandler.post(command)
        }
    }
}
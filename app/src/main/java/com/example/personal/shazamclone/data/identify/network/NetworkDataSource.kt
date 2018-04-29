package com.example.personal.shazamclone.data.identify.network

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by personal on 4/26/2018.
 */
class NetworkDataSource{

    companion object {

        val instance by lazy { NetworkDataSource() }

        val BASE_URL: String = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo"

        val BASE_URL_MB = "https://musicbrainz.org/ws/2/isrc/"

        val ID = "id"
        val API_KEY = "api_key"
        val API_KEY_VALUE = "22ebf268c1bab92ad08463f9fc8936d2"

        val ALBUM = "album"

        val MBID = "mbid"

        val IMAGES = "image"

        val AUTO_CORRECT = "autocorrect"

        val FORMAT = "format"

        val FORMAT_VALUE = "json"

        val SIZE = "size"

        val SIZE_VALUE = "medium"

        val TEXT = "#text"

        val ERROR = "error"

        val FORMAT_MB = "fmt"

    }

    val interceptor : HttpLoggingInterceptor by lazy { HttpLoggingInterceptor() }

    val okhttpBuilder : OkHttpClient.Builder by lazy { OkHttpClient.Builder() }

val client : OkHttpClient by lazy { interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

okhttpBuilder.networkInterceptors().add(interceptor)

    okhttpBuilder.build()
}


    fun getAlbumArtUrl(isrc: String) : String{

        Log.d("NetworkDataSource", "isrc code is $isrc")

        val responseMB : Response = client.newCall(requestBuilderMB(isrc)).execute()

        val mbId = parseResponseMB(responseMB.body()!!.string())

        val response : Response = client.newCall(requestBuilder(mbId)).execute()


        val imageURL = parseResponse(response.body()!!.string())

        return imageURL
    }

    private fun requestBuilder(mbId : String) : Request {

        val urlBuilder : HttpUrl.Builder = HttpUrl.parse(BASE_URL)!!.newBuilder()

        urlBuilder.addQueryParameter(API_KEY, API_KEY_VALUE)
        urlBuilder.addQueryParameter(MBID, mbId)
        urlBuilder.addQueryParameter(FORMAT, FORMAT_VALUE)
        urlBuilder.addQueryParameter(AUTO_CORRECT, "1")

        val url : String = urlBuilder.build().toString()

        return Request.Builder()
                .url(url)
                .build()
    }

    private fun requestBuilderMB(isrc : String) : Request {

        val urlBuilder : HttpUrl.Builder = HttpUrl.parse(BASE_URL_MB)!!.newBuilder()

        urlBuilder.addPathSegment(isrc)

        urlBuilder.addQueryParameter(FORMAT_MB, "json")

        val url : String = urlBuilder.build().toString()

        return Request.Builder()
                .url(url)
                .build()
    }

    private fun parseResponse(responseString : String): String {

        Log.d("NetworkDataSource ", "the response is $responseString")

        var imageUrl = ""

        try {

            val rootObject = JSONObject(responseString)

            if(rootObject.has(ERROR)){

                return imageUrl
            }
            val track = rootObject.getJSONObject("track")
            val album: JSONObject = track.getJSONObject(ALBUM)
            val images: JSONArray = album.getJSONArray(IMAGES)

            for (i in 0..(images.length() - 1)) {


                if (images.getJSONObject(i).getString(SIZE).equals(SIZE_VALUE)) {

                    imageUrl = images.getJSONObject(i).getString(TEXT)
                }

            }

        } catch(exception : JSONException){


        }

        return imageUrl
    }

    private fun parseResponseMB(responseString : String) : String{

        var mbId = ""

        try{

            val rootObject = JSONObject(responseString)

            val recordingArray = rootObject.getJSONArray("recordings")

            mbId = recordingArray.getJSONObject(0).getString("id")
        }
        catch (exception : JSONException){


        }

        return mbId
    }

}
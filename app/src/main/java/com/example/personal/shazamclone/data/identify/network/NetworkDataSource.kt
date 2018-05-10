package com.example.personal.shazamclone.data.identify.network

import android.util.Log
import fr.arnaudguyon.xmltojsonlib.XmlToJson
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
class NetworkDataSource {

    companion object {

        val instance by lazy { NetworkDataSource() }


        val BASE_URL_MB = "https://musicbrainz.org/ws/2/isrc/"

        val BASE_URL_CA = "https://coverartarchive.org/release/"


        val INC = "inc"

        val INC_VAL = "releases"


    }

    val interceptor: HttpLoggingInterceptor by lazy { HttpLoggingInterceptor() }

    val okhttpBuilder: OkHttpClient.Builder by lazy { OkHttpClient.Builder() }

    val client: OkHttpClient by lazy {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        okhttpBuilder.networkInterceptors().add(interceptor)

        okhttpBuilder.build()
    }

    fun getCoverArtUrl(isrc: String = "", track: String = "", artist: String = ""): String {

        Log.d("NetworkDataSource", "isrc code is $isrc and get getCoverArtUrl called")

        var imageUrl: String = ""

        if (!isrc.isNullOrEmpty()) {

            val responseMB: Response = client.newCall(requestBuilderMB(isrc)).execute()

            val id: String = parseResponseMB(responseMB.body()!!.string())

            Log.d("NetworkDataSource", "the id of the album is $id")

            imageUrl = requestCoverArtArchive(id)


        }
        /*else
        {
             do a api call using track, artist and album.
        }*/

        return imageUrl
    }


    private fun requestBuilderMB(isrc: String): Request {

        val urlBuilder: HttpUrl.Builder = HttpUrl.parse(BASE_URL_MB)!!.newBuilder()

        urlBuilder.addPathSegment(isrc)

        urlBuilder.addQueryParameter(INC, INC_VAL)

        val url: String = urlBuilder.build().toString()

        return Request.Builder()
                .url(url)
                .build()
    }


    private fun parseResponseMB(responseString: String): String {

        Log.d("NetworkDataSource", "the response from MB(XML) is $responseString")

        val xmlToJson: XmlToJson = XmlToJson.Builder(responseString).build()

        Log.d("NetworkDataSource", "the response from MB(JSON) is $xmlToJson")


        var id: String = ""

        try {

            val rootObject = xmlToJson.toJson()

            val metadata = rootObject!!.getJSONObject("metadata")

            val isrc = metadata!!.getJSONObject("isrc")

            val recordingList = isrc!!.getJSONObject("recording-list")

            var recording = recordingList!!.get("recording")

            var releaseList : JSONObject = JSONObject()

            if(recording is JSONArray) {

               id = getFirstReleaseWithRecordingArray(recording)

                /*recording = recording as JSONArray
                releaseList =  recording.getJSONObject(0).getJSONObject("release-list")*/
            }
            else
            {
                recording = recording as JSONObject
                releaseList = recording.getJSONObject("release-list")
            }


            var release = releaseList!!.get("release")

            if(release is JSONArray)
            {
                id = getFirstRelease(release).get(1)
                //id = releaseList.getJSONArray("release").getJSONObject(0).getString("id")
            }
            else{

                id = releaseList.getJSONObject("release").getString("id")
            }



            return id


        } catch (exception: JSONException) {

            exception.printStackTrace()
        }

        Log.d("NetworkDataSource", "the id of the album is $id")

        return id
    }


   private fun requestCoverArtArchive(releaseId: String): String {

        val response: Response = client.newCall(createRequestCA(releaseId)).execute()

        val url = parseCAresponse(response.body()!!.string())

        Log.d("NetworkDataSource", "the url from cover art is $url")

        return url
    }

    private fun createRequestCA(releaseId: String): Request {

        val urlBuilder: HttpUrl.Builder = HttpUrl.parse(BASE_URL_CA)!!.newBuilder()

        urlBuilder.addPathSegment(releaseId)

        val url = urlBuilder.build().toString()

        return Request.Builder().url(url).build()
    }

   private fun parseCAresponse(response: String): String {

        Log.d("NetworkDataSource", "the response from cover art is $response")

        var small : String = ""

       try {

           val rootObject = JSONObject(response)
           val image = rootObject.getJSONArray("images").getJSONObject(0)
           val thumbnails = image.getJSONObject("thumbnails")
           small = thumbnails.getString("small")
       }
       catch (exception: JSONException) {

           exception.printStackTrace()
       }

        return small
    }


    private fun getFirstRelease(releases : JSONArray) : MutableList<String> {

        val releaseList = mutableListOf<String>()
        val releaseIdList = mutableListOf<String>()
        val resultList = mutableListOf<String>()

        for(i in 0 until releases.length())
        {
            releaseList.add(releases.getJSONObject(i).getString("date"))
            releaseIdList.add(releases.getJSONObject(i).getString("id"))
        }

        val firstReleaseDate = releaseList.min()
        resultList.add(firstReleaseDate!!)

        Log.d("NetworkDataSource", "the first release date is $firstReleaseDate")

        val firstReleaseDateIndex = releaseList.indexOf(firstReleaseDate)



        val firstReleaseId = releaseIdList.get(firstReleaseDateIndex)

        resultList.add(firstReleaseId)

        Log.d("NetworkDataSource", "the first release id is  $firstReleaseId")


        return resultList

    }

    private fun getFirstReleaseWithRecordingArray(recordings : JSONArray) : String {

        val releaseIdList = mutableListOf<String>()
        val releaseDate = mutableListOf<String>()

        var id : String = ""

        for(i in 0 until recordings.length()){

            val releaseList = recordings.getJSONObject(i).getJSONObject("release-list")

            var release = releaseList!!.get("release")

            if(release is JSONArray)
            {
                releaseDate.add(getFirstRelease(release).get(0))
                releaseIdList.add(getFirstRelease(release).get(1))
            }
            else{

                id = releaseList.getJSONObject("release").getString("id")
            }

        }

        val index = releaseDate.indexOf(releaseDate.min())

        Log.d("NetworkDataSource", "the index of the overall first date is $index")
        id = releaseIdList.get(index)
        Log.d("NetworkDataSource", "the id of the overall first release is $id")

        return id
    }

}
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
 * This class is responsible for doing all the network calls directly
 */
class NetworkDataSource {

    // for all the static objects of this class
    companion object {

        // returns an instance of this class. Since we are using a lazy delegate we make it a singleton
        val instance by lazy { NetworkDataSource() }

        // this url is used to do a isrc lookup in the musicbrainz api. This will return a release id
        // if the track
        val BASE_URL_MB = "https://musicbrainz.org/ws/2/isrc/"

        // this url is used to get the cover art url using the release id we get from the previous url
        val BASE_URL_CA = "https://coverartarchive.org/release/"


        val INC = "inc"

        val INC_VAL = "releases"


    }

    // interceptor for all the logging in okHttp
    val interceptor: HttpLoggingInterceptor by lazy { HttpLoggingInterceptor() }

    // creating a singleton of the okhttpBuilder
    val okhttpBuilder: OkHttpClient.Builder by lazy { OkHttpClient.Builder() }

    // finally creating a singleton object of the OkHttpClient
    val client: OkHttpClient by lazy {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        okhttpBuilder.networkInterceptors().add(interceptor)

        okhttpBuilder.build()
    }

    // this method is responsible for getting the cover art url. It uses isrc to fetch the details.
    // sometimes isrc of a track can be null so we need to need to use the track name and artist name
    // to find the details. Still have to implement that
    fun getCoverArtUrl(isrc: String = "", track: String = "", artist: String = ""): String {

        Log.d("NetworkDataSource", "isrc code is $isrc and get getCoverArtUrl called")

        var imageUrl: String = ""

        //if we have the isrc we will do the isrc lookup in the MusicBrainz api
        if (!isrc.isNullOrEmpty()) {

            // using the client to make a call to the MusicBrainz api and collecting the response
            val responseMB: Response = client.newCall(requestBuilderMB(isrc)).execute()

            // we got the resonse now we need to parse it and get a release id
            val id: String = parseResponseMB(responseMB.body()!!.string())

            Log.d("NetworkDataSource", "the id of the album is $id")

            // here we get the image url by using the function and passing the release id we got before
            imageUrl = requestCoverArtArchive(id)


        }
        /*else
        {
             do a api call using track, artist and album.
        }*/

        return imageUrl
    }

   // this method will build the reqeust object for MusicBrainz api call
    private fun requestBuilderMB(isrc: String): Request {

       // building the url
        val urlBuilder: HttpUrl.Builder = HttpUrl.parse(BASE_URL_MB)!!.newBuilder()

      // this will add the isrc code eg:  https://musicbrainz.org/ws/2/isrc/UA012789
        urlBuilder.addPathSegment(isrc)

       // this will add the query parameter eg: https://musicbrainz.org/ws/2/isrc/UA012789?inc=releases
        urlBuilder.addQueryParameter(INC, INC_VAL)

        val url: String = urlBuilder.build().toString()

       // finally returning the reqeust object
        return Request.Builder()
                .url(url)
                .build()
    }


    // this method will parse the response from the musicbrainz api so that we can get the release
    // id of the track. It takes the response as a string and returns release id as a string
    private fun parseResponseMB(responseString: String): String {

        Log.d("NetworkDataSource", "the response from MB(XML) is $responseString")

        // since MusicBrainz json api is still in beta and was broken at the time we had to use
        // xml. So I am using a library to convert xml to json(https://github.com/smart-fun/XmlToJson)
        val xmlToJson: XmlToJson = XmlToJson.Builder(responseString).build()

        Log.d("NetworkDataSource", "the response from MB(JSON) is $xmlToJson")


        // variable to store the release id
        var id: String = ""

        try {

            val rootObject = xmlToJson.toJson()

            if(rootObject!!.has("error"))
            {
                return ""
            }

            val metadata = rootObject!!.optJSONObject("metadata")

            val isrc = metadata!!.optJSONObject("isrc")

            val recordingList = isrc!!.optJSONObject("recording-list")

            // sometimes recording can be an array of json objects or sometimes just a json object
            // which is not in our control so we have to take care of that situation
            var recording = recordingList!!.get("recording")

            var releaseList : JSONObject = JSONObject()

            // check if recording is a json array
            if(recording is JSONArray) {

                // this method will take recording array and then loop over it to get the first release id
               id = getFirstReleaseWithRecordingArray(recording).values.elementAt(0)
            }
            // if recording is not an array we can then just get the id from the recoding object
            else
            {
                recording = recording as JSONObject
                releaseList = recording.optJSONObject("release-list")

                // again we are not sure if release will be a json array or json object. so we need
                // to do a check on that and then act accordingly
                var release = releaseList!!.get("release")

                //if release is an array
                if(release is JSONArray)
                {
                    //get the id of the release by using the function and passing the release list
                    id = getFirstRelease(release).values.elementAt(0)
                    //id = releaseList.getJSONArray("release").getJSONObject(0).getString("id")
                }
                else{

                    id = releaseList.optJSONObject("release").optString("id")
                }
            }

            return id


        } catch (exception: JSONException) {

            exception.printStackTrace()
        }

        Log.d("NetworkDataSource", "the id of the album is $id")

        return id
    }


    // this method simpley takes the relase id and do a search on the coverart archive api.
    // it then returns the response with the image urls
   private fun requestCoverArtArchive(releaseId: String): String {

        // call the api and fetch the response https://coverartarchive.org/release/{id}
        val response: Response = client.newCall(createRequestCA(releaseId)).execute()

        // pass the response as a string to the function and then get the url
        val url = parseCAresponse(response.body()!!.string())

        Log.d("NetworkDataSource", "the url from cover art is $url")

        return url
    }

    // this method just creates a request object for cover art archive api
    private fun createRequestCA(releaseId: String): Request {

        val urlBuilder: HttpUrl.Builder = HttpUrl.parse(BASE_URL_CA)!!.newBuilder()

        urlBuilder.addPathSegment(releaseId)

        val url = urlBuilder.build().toString()

        return Request.Builder().url(url).build()
    }

    // this function will parse the response from the cover art archive and return a url string
   private fun parseCAresponse(response: String): String {

        Log.d("NetworkDataSource", "the response from cover art is $response")

        var small : String = ""

       try {

           val rootObject = JSONObject(response)
           val image = rootObject.optJSONArray("images").optJSONObject(0)
           val thumbnails = image.optJSONObject("thumbnails")
           small = thumbnails.optString("small")
       }
       catch (exception: JSONException) {

           exception.printStackTrace()
       }

        return small
    }

    // this method will get the json array of releases and retrun a map with date mapped to release id
    private fun getFirstRelease(releases : JSONArray) : MutableMap<String,String> {

        val releaseList = mutableListOf<String>()
        val releaseIdList = mutableListOf<String>()
        val resultList = mutableListOf<String>()

        val result = mutableMapOf<String, String>()

        for(i in 0 until releases.length())
        {
            releaseList.add(releases.optJSONObject(i).optString("date"))
            releaseIdList.add(releases.optJSONObject(i).optString("id"))

            result.put(releases.optJSONObject(i).optString("date"),
                    releases.optJSONObject(i).optString("id") )
        }

        val firstReleaseDate = releaseList.min()

        val sorted = result.toSortedMap()
        resultList.add(firstReleaseDate!!)

        Log.d("NetworkDataSource", "the first release date is $firstReleaseDate")

        val firstReleaseDateIndex = releaseList.indexOf(firstReleaseDate)



        val firstReleaseId = releaseIdList.get(firstReleaseDateIndex)

        resultList.add(firstReleaseId)

        Log.d("NetworkDataSource", "the first release id is  $firstReleaseId")

        Log.d("NetworkDataSource", "the result map is ${sorted.toString()}")


        return sorted

    }


    // this method is called when we have more than one recordings. It returns a map
    private fun getFirstReleaseWithRecordingArray(recordings : JSONArray) : MutableMap<String,String> {

        // here we initialize the map that will store the result. We will use the date of the
        // release as key and the release id as its value. We do this so that we can sort the map
        // to get the first release.
        val result = mutableMapOf<String, String>()

       // iterate over all the recordings
        for(i in 0 until recordings.length()){

            val releaseList = recordings.optJSONObject(i).optJSONObject("release-list")

            // again here too release can be a json array or an object
            var release = releaseList!!.get("release")

            if(release is JSONArray)
            {
                // here we use the method that returns a map of result

                result.putAll(getFirstRelease(release))

            }
            else{

                // just one release object so get the date and release id an put it in the map
                result.put(releaseList.optJSONObject("release").optString("date"),
                        releaseList.optJSONObject("release").optString("id"))
            }

        }

        // sorting the map by dates so that we can get the first release which has more chances of
        // giving the correct cover art
        val sorted = result.toSortedMap()

        Log.d("NetworkDataSource", "the sorted map is $sorted")

        return sorted
    }

}
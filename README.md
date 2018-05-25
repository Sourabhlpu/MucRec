## Project title
This android app can help users identify the music which is plaing around by just a tap. Its called **MucRec** written in **Kotlin**

## Motivation
The motivation behind this project is purely self learning by making apps. I wanted to see if I can implement the idea of the app myself.
This app is also inspired by [this article](https://android.jlelse.eu/building-a-shazam-clone-part-1-building-android-apps-series-179c2580a094)

 
## Screenshots
There are three main screens of the app
<p align="center">
  <img src="https://user-images.githubusercontent.com/17663945/40562695-b66236a0-607f-11e8-81cc-215d85317d5d.png">&nbsp &nbsp  <img src="https://user-images.githubusercontent.com/17663945/40562730-d70f7cf0-607f-11e8-811f-9946471e0c76.png">&nbsp &nbsp <img src="https://user-images.githubusercontent.com/17663945/40562736-dbe67ff8-607f-11e8-9d0b-5348311cb9d4.png">
</p>                                                                                                                                                                                                                                 

## Features
* Tap on the home screen when you want to identify the music.
* If identified successfully the song details page will show all the details of the song and will also be able to play the video of the song.
* In the history screen user can see all the songs that were identified previously.


## Libraries/API's used

* Used [ACRCloud Android sdk](https://www.acrcloud.com/docs/acrcloud/audio-fingerprinting-sdks/android-sdk/) for the core functionality of the app i.e. to identify the music.
* [SpinKit library](https://github.com/ybq/Android-SpinKit) that causes the beautiful ripple animation in the home screen when identification starts.
* YouTube android apis for doing a youtube search and embedding a YouTube player in the fragment.
* Room database library for local persistance.
* [MusicBrainz api](https://musicbrainz.org/doc/Development/XML_Web_Service/Version_2) to get the extra metadata of the track specifically the album art.
* [XmlToJson](https://github.com/smart-fun/XmlToJson) for xml to json conversion.



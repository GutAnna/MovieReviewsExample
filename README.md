MovieReviews
==================================

Load movie's reviews from https://developer.nytimes.com/apis.

Introduction
------------

The data is stored on a Web server as a REST web service.  
The solution app demonstrate the use of:
[Retrofit](https://square.github.io/retrofit/) to make REST requests to the web service,
[Moshi](https://github.com/square/moshi) to handle the deserialization of the returned JSON to Kotlin data objects, and 
[Coil](https://coil-kt.github.io/coil/) to load images by URL.
[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel),
[LiveData](https://developer.android.com/topic/libraries/architecture/livedata), and
[Data Binding](https://developer.android.com/topic/libraries/data-binding/) with binding 
adapters.
[Navigation](https://developer.android.com/guide/navigation) with [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args)




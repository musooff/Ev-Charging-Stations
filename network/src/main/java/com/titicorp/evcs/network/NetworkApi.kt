package com.titicorp.evcs.network

import com.titicorp.evcs.network.model.NetworkStation
import com.titicorp.evcs.network.model.NetworkStationDetails
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkApi @Inject constructor() {

    private val client = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply { setLevel(HttpLoggingInterceptor.Level.BASIC) },
        )
        .addInterceptor(MockInterceptor())
        .build()

    private val service = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NetworkService::class.java)

    suspend fun getNearbyStations(): List<NetworkStation> = service.getNearbyStations()

    suspend fun getSavedStations(): List<NetworkStation> = service.getSavedStations()

    suspend fun getStationDetails(id: String): NetworkStationDetails = service.getStationDetails(id = id)
}

private const val BASE_URL = "https://google.com"

private class MockInterceptor : Interceptor {

    private val sample = """
        {
            "id": "1",
            "title": "First Birmingham Station",
            "description": "",
            "address": "Park Avenue 110",
            "lat": 38.574106716422506,
            "lng": 68.78535232665297,
            "thumbnail": "https://images.unsplash.com/photo-1574867154971-fd59e11395b2?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=1080&ixid=MnwxfDB8MXxyYW5kb218MHx8Z2FzIHN0YXRpb24nfHx8fHx8MTY5NjY1MDA5Mw&ixlib=rb-4.0.3&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1920",
            "chargers":
            [],
            "reviews":
            []
        }
    """.trimIndent()

    private val nearby = """
        [
            {
                "id": "1",
                "title": "First Birmingham Station",
                "address": "Park Avenue 110",
                "lat": 38.574106716422506,
                "lng": 68.78535232665297,
                "thumbnail": "https://images.unsplash.com/photo-1574867154971-fd59e11395b2?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=1080&ixid=MnwxfDB8MXxyYW5kb218MHx8Z2FzIHN0YXRpb24nfHx8fHx8MTY5NjY1MDA5Mw&ixlib=rb-4.0.3&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1920"
            },
            {
                "id": "2",
                "title": "Second Birmingham Station",
                "address": "Park Avenue 110",
                "lat": 38.578661705786544,
                "lng": 68.78273075220778,
                "thumbnail": "https://images.unsplash.com/photo-1599687634327-19ce84c8431b?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=1080&ixid=MnwxfDB8MXxyYW5kb218MHx8Z2FzIHN0YXRpb24nfHx8fHx8MTY5NjY1MDI1OQ&ixlib=rb-4.0.3&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1920"
            },
            {
                "id": "3",
                "title": "Third Birmingham Station",
                "address": "Park Avenue 110",
                "lat": 38.58096931688277,
                "lng": 68.7863539751907,
                "thumbnail": "https://images.unsplash.com/photo-1611672672928-63f9e240921e?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=1080&ixid=MnwxfDB8MXxyYW5kb218MHx8Z2FzIHN0YXRpb24nfHx8fHx8MTY5NjY1MDIzNA&ixlib=rb-4.0.3&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1920"
            },
            {
                "id": "4",
                "title": "Fourth Birmingham Station",
                "address": "Park Avenue 110",
                "lat": 38.59096931688277,
                "lng": 68.7863539751907,
                "thumbnail": "https://images.unsplash.com/photo-1578915684234-3a35d041c7a3?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=1080&ixid=MnwxfDB8MXxyYW5kb218MHx8Z2FzIHN0YXRpb24nfHx8fHx8MTY5NjY1MDIxMw&ixlib=rb-4.0.3&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1920"
            }
        ]
    """.trimIndent()

    override fun intercept(chain: Interceptor.Chain): Response {
        val message = ""
        Thread.sleep(1_000)
        val body = when (chain.request().url.encodedPath) {
            "/stations/nearby" -> nearby
            "/stations/saved" -> nearby
            else -> sample
        }.toResponseBody("application/json".toMediaTypeOrNull())
        return Response.Builder()
            .protocol(Protocol.HTTP_1_1)
            .request(chain.request())
            .code(200)
            .body(body)
            .message(message)
            .build()
    }
}

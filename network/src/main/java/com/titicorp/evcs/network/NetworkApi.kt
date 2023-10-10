package com.titicorp.evcs.network

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.titicorp.evcs.network.model.Auth
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

    suspend fun login(phoneNumber: String, password: String) = service.login(
        body = Auth.LoginRequest(
            phoneNumber = phoneNumber,
            password = password,
        ),
    )

    suspend fun register(name: String, phoneNumber: String, password: String) = service.register(
        body = Auth.RegisterRequest(
            name = name,
            phoneNumber = phoneNumber,
            password = password,
        ),
    )
}

private const val BASE_URL = "https://google.com"

private class MockInterceptor : Interceptor {

    private val description = """
        Welcome to our Shell Gas Station, where we provide you with top-quality fuels and exceptional service. At Shell, we are committed to fueling your journeys with clean and efficient energy solutions. Our well-maintained station offers a wide range of fuels, including premium and diesel options, ensuring that your vehicle gets the care it deserves.

        But we're more than just a place to fill up your tank. Our convenience store is stocked with a variety of snacks, beverages, and convenience items to keep you refreshed and satisfied during your travels. Need a quick coffee pick-me-up or a tasty snack for the road? We've got you covered.

        At Shell, we value your time and safety. Our friendly and knowledgeable staff is here to assist you with any questions or concerns you may have. We strive to make your visit as convenient and pleasant as possible.

        Whether you're on a long road trip or just need to refuel locally, Shell is your trusted partner for quality fuel and convenience. Stop by our Shell Gas Station today and experience the difference.
    """.trimIndent()

    private val stations = """
        [
            {
                "id": "1",
                "title": "Chevron EV Station",
                "address": "789 Fuel Station Road, Birmingham, UK",
                "lat": 38.578661705786544,
                "lng": 68.78273075220778,
                "thumbnail": "https://images.unsplash.com/photo-1599687634327-19ce84c8431b?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=1080&ixid=MnwxfDB8MXxyYW5kb218MHx8Z2FzIHN0YXRpb24nfHx8fHx8MTY5NjY1MDI1OQ&ixlib=rb-4.0.3&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1920"
            },
            {
                "id": "2",
                "title": "ExxonMobil Electric Vehicle Charging Station",
                "address": "456 Petroleum Avenue, Birmingham, UK",
                "lat": 38.574106716422506,
                "lng": 68.78535232665297,
                "thumbnail": "https://images.unsplash.com/photo-1574867154971-fd59e11395b2?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=1080&ixid=MnwxfDB8MXxyYW5kb218MHx8Z2FzIHN0YXRpb24nfHx8fHx8MTY5NjY1MDA5Mw&ixlib=rb-4.0.3&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1920"
            },
            {
                "id": "3",
                "title": "Sunoco Chargers",
                "address": "101 Petro Avenue, Birmingham, UK",
                "lat": 38.58096931688277,
                "lng": 68.7863539751907,
                "thumbnail": "https://images.unsplash.com/photo-1611672672928-63f9e240921e?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=1080&ixid=MnwxfDB8MXxyYW5kb218MHx8Z2FzIHN0YXRpb24nfHx8fHx8MTY5NjY1MDIzNA&ixlib=rb-4.0.3&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1920"
            },
            {
                "id": "4",
                "title": "Valero Station",
                "address": "8 Gasoline Lane, Birmingham, UK",
                "lat": 38.59096931688277,
                "lng": 68.7863539751907,
                "thumbnail": "https://images.unsplash.com/photo-1578915684234-3a35d041c7a3?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=1080&ixid=MnwxfDB8MXxyYW5kb218MHx8Z2FzIHN0YXRpb24nfHx8fHx8MTY5NjY1MDIxMw&ixlib=rb-4.0.3&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1920"
            }
        ]

    """.trimIndent()

    override fun intercept(chain: Interceptor.Chain): Response {
        val message = ""
        Thread.sleep(1_000)
        val url = chain.request().url
        val body = when (url.encodedPath) {
            "/auth/login", "/auth/register" -> {
                val user = JsonObject().apply {
                    addProperty("phoneNumber", "010111222")
                    addProperty("name", "Thomas Shelby")
                }
                user.toString()
            }

            "/stations/nearby" -> stations
            "/stations/saved" -> stations
            else -> {
                val id = url.queryParameter("id")
                val stations = Gson().fromJson(stations, JsonArray::class.java).asJsonArray
                val station = stations.first() {
                    it.asJsonObject.get("id").asString == id
                }
                station.asJsonObject.apply {
                    addProperty("description", description)
                    add("chargers", JsonArray())
                    add("reviews", JsonArray())
                }
                station.toString()
            }
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

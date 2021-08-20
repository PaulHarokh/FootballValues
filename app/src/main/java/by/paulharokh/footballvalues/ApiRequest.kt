package by.paulharokh.footballvalues

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://transfermarket.p.rapidapi.com/players/"

interface ApiRequest {

    @GET("get-header-info")
    fun getFootballer(
        @Query("id") id: Int,
        @Header("X-RapidAPI-Host") api: String?,
        @Header("X-RapidAPI-Key") apiKey: String?
    ): Call<FootballerHeader>

    companion object Factory {
        fun create(): ApiRequest {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(ApiRequest::class.java)
        }
    }

}
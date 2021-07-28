package by.paulharokh.footballvalues


import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


const val BASE_URL = "https://transfermarket.p.rapidapi.com/players/get-market-value/"

interface ApiRequest {

    @GET("market")
    fun getMarket( @Query("id") id: String): Call<Int>

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


//            val request = Request.Builder()
//                .url("https://transfermarket.p.rapidapi.com/players/get-market-value?id=74842")
//                .get()
//                .addHeader("x-rapidapi-host", "transfermarket.p.rapidapi.com")
//                .build()
//
//            val response = okHttpClient.newCall(request).execute()


            return retrofit.create(ApiRequest::class.java)
        }

    }
}
package com.deneb.david.astroapp.data.api

import com.google.gson.JsonElement
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by alnit on 30/01/2018.
 */
interface AstrobinApi {
    companion object Factory {
        fun create(): AstrobinApi {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://astrobin.com")
                    .build()

            return retrofit.create(AstrobinApi::class.java)
        }
    }

    @GET("/api/v1/imageoftheday/")
    fun getImageOfTheDay(@Query("limit") limit:String,
                         @Query("api_key") apiKey:String,
                         @Query("api_secret") apiSecret:String,
                         @Query("format") format:String) : Flowable<JsonElement>

    @GET("/api/v1/image/{id}/")
    /*@GET("{id}")*/
    fun getOneImage(@Path("id") id:String,
                    @Query("api_key") apiKey:String,
                    @Query("api_secret") apiSecret:String,
                    @Query("format") format:String) : Flowable<JsonElement>

}
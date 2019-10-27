package com.diayan.kaal.api

import androidx.lifecycle.LiveData
import com.diayan.kaal.data.model.Event
import com.diayan.kaal.data.model.Place
import com.diayan.kaal.data.model.Store
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val ENDPOINT = "http://demo8398315.mockable.io/"
    }

    @GET("events")
    suspend fun getEvents(@Query("page") page: Int? = null,
                          @Query("page_size") pageSize: Int? = null,
                          @Query("ordering") order: String? = null): Response<ResultsResponse<Event>>

    @GET("stores")
    suspend fun getStores(@Query("page") page: Int? = null,
                        @Query("page_size") pageSize: Int? = null,
                        @Query("ordering") order: String? = null): Response<ResultsResponse<Store>>



    @GET("places")
    suspend fun getPlaces(@Query("page") page: Int? = null,
                        @Query("page_size") pageSize: Int? = null,
                        @Query("theme_id") themeId: Int? = null,
                        @Query("ordering") order: String? = null): Response<ResultsResponse<Place>>


//dummy endpoints data for testing
    @GET("events")
    fun getAllEvents(): LiveData<List<Event>>

    @GET("stores")
    fun getAllStores(): LiveData<List<Store>>

    @GET("places")
    fun getAllPlaces(): LiveData<List<Place>>

    @GET("kaal/event/{id}/")
    suspend fun getEvent(@Path("id") id: String): Response<Event>

    @GET("kaal/place/{id}/")
    suspend fun getPlace(@Path("id") id: String) : Response<Place>

    @GET("kaal/store/{id}/")
    suspend fun getStore(@Path("id") id: String) : Response<Store>
}
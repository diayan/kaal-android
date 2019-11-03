package com.diayan.kaal.di

import android.app.Application
import com.diayan.kaal.BuildConfig
import com.diayan.kaal.api.ApiService
import com.diayan.kaal.api.AuthIntercepter
import com.diayan.kaal.data.AppDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(
        @AppApi okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okHttpClient, converterFactory, ApiService::class.java)


    @AppApi
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
            .addInterceptor(AuthIntercepter(BuildConfig.APPLICATION_ID))
            .build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun providePlacesDao(db: AppDatabase) = db.placesDao()


    @Singleton
    @Provides
    fun provideStoreDao(db: AppDatabase) = db.StoresDao()

    @Singleton
    @Provides
    fun provideEventDao(db: AppDatabase) = db.eventsDao()

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    private fun createRetrofit(okHttpClient: OkHttpClient,
                               converterFactory: GsonConverterFactory): Retrofit{
        return Retrofit.Builder()
            .baseUrl(ApiService.ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
    private fun <T> provideService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory, clazz: Class<T>
    ): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }
}
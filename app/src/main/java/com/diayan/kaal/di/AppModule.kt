package com.diayan.kaal.di

import android.app.Application
import com.diayan.kaal.data.AppDatabase
import com.diayan.kaal.data.dao.OnBoardingDao
import com.diayan.kaal.data.repository.OnBoardingRepository
import com.diayan.kaal.helper.FirebaseAuthHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @InternalCoroutinesApi
    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun providePlacesDao(db: AppDatabase) = db.placesDao()


    @Singleton
    @Provides
    fun provideStoreDao(db: AppDatabase) = db.storesDao()

    @Singleton
    @Provides
    fun provideEventDao(db: AppDatabase) = db.eventsDao()

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun funFirebaseAuthentication() = FirebaseAuth.getInstance()


    @Provides
    @Singleton
    fun providesFirebaseHelper(): FirebaseAuthHelper = FirebaseAuthHelper()

    @Provides
    @Singleton
    fun providesOnBoardingDao(
        instance: FirebaseFirestore,
        helper: FirebaseAuthHelper
    ) = OnBoardingDao(instance, helper)

    @Provides
    @Singleton
    fun providesOnBoardingRepository(onBoardingDao: OnBoardingDao) = OnBoardingRepository(onBoardingDao)
}
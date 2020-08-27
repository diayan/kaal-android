package com.diayan.kaal.di

import android.app.Application
import com.diayan.kaal.data.AppDatabase
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
}
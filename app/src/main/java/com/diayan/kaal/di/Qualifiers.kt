package com.diayan.kaal.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AppApi

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScopeIO
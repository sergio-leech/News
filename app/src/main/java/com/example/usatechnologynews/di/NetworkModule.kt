package com.example.usatechnologynews.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule{
    @Singleton
    @Provides
    fun provideHttpClient():HttpClient{
        return  HttpClient(Android) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Ktor", message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
}
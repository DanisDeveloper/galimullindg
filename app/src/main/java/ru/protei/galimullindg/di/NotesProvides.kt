package ru.protei.galimullindg.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.protei.galimullindg.data.local.NotesDao
import ru.protei.galimullindg.data.local.NotesDatabase
import ru.protei.galimullindg.data.remote.NotesGitHubApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotesProvides {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            "notes_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(database: NotesDatabase): NotesDao {
        return database.notesDao()
    }

    @Provides
    @Singleton
    fun provideHTTPClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer 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"
                    ).build()
                chain.proceed(request)
            }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/repos/DanisDeveloper/galimullindg/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNotesGitHubApi(retrofit: Retrofit): NotesGitHubApi {
        return retrofit.create(NotesGitHubApi::class.java)
    }

}
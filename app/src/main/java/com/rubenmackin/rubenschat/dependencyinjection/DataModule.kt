package com.rubenmackin.rubenschat.dependencyinjection

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.rubenmackin.rubenschat.data.database.DatabaseServiceImpl
import com.rubenmackin.rubenschat.data.network.FirebaseChatService
import com.rubenmackin.rubenschat.domain.DatabaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDatabaseReference() = Firebase.database.reference

    @Singleton
    @Provides
    fun provideFirebaseService(reference: DatabaseReference) = FirebaseChatService(reference)

    @Singleton
    @Provides
    fun provideDataStoreService(@ApplicationContext context: Context): DatabaseService {
        return DatabaseServiceImpl(context)
    }
}
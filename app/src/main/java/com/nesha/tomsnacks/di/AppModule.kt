package com.nesha.tomsnacks.di

import android.app.Application
import androidx.room.Room
import com.nesha.tomsnacks.data.local.TomSnackDao
import com.nesha.tomsnacks.data.local.TomSnackDatabase
import com.nesha.tomsnacks.data.repository.TomSnackRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): TomSnackDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = TomSnackDatabase::class.java,
            name = "tomsnack_db"
        ).createFromAsset("database/tomsnack_db.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideTomSnackDao(
        tomSnackDatabase: TomSnackDatabase
    ): TomSnackDao = tomSnackDatabase.tomSnackDao

    @Provides
    @Singleton
    fun provideTomSnackRepository(
        tomSnackDao: TomSnackDao
    ) = TomSnackRepository(tomSnackDao)
}
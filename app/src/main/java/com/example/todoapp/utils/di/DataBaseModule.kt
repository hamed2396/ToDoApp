package com.example.todoapp.utils.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.db.ToDoDataBase
import com.example.todoapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ToDoDataBase::class.java, Constants.TODO_DATABASE).build()

    @Singleton
    @Provides
    fun provideDao(dataBase: ToDoDataBase) = dataBase.todoDao()

}
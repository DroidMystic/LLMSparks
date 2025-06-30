package com.example.llmsparks.di

import androidx.room.Room
import com.example.llmsparks.data.local.AppDatabase
import com.example.llmsparks.data.remote.PromptRemoteDataSource
import com.example.llmsparks.presentation.viewmodel.PromptViewModel
import com.example.llmsparks.repository.PromptRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "llm_sparks_db"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<AppDatabase>().promptDao() }
    single { get<AppDatabase>().bookmarkDao() }
    single { PromptRemoteDataSource() }
    single { PromptRepository(get(), get(), get()) }
    viewModel { PromptViewModel(get()) }

}
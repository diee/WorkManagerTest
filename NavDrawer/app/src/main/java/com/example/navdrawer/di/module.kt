package com.example.navdrawer.di

import com.example.navdrawer.data.AppDatabase
import com.example.navdrawer.example.MainViewModel
import com.example.navdrawer.example.interactor.GetPersonNetworkUseCase
import com.example.navdrawer.example.interactor.GetPersonUseCase
import com.example.navdrawer.example.interactor.SavePersonNetworkUseCase
import com.example.navdrawer.example.interactor.SavePersonUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule: Module =
    module {

        single { AppDatabase.get(get()) }
        single { get<AppDatabase>().personDao() }
    }

val uiModule: Module =
    module {
        viewModel { MainViewModel(get(), get(), get(), get()) }
    }

val domainModule: Module =
    module {
        factory { SavePersonUseCase(get()) }
        factory { GetPersonUseCase(get()) }
        factory { GetPersonNetworkUseCase(get()) }
        factory { SavePersonNetworkUseCase(get()) }
    }
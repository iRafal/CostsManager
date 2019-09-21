package com.andrii.costsmanager.di

import com.andrii.costsmanager.data.CategoryRepository
import com.andrii.costsmanager.data.storage.CategoryDataBase
import com.andrii.costsmanager.data.storage.CategoryLocalRepository
import com.andrii.costsmanager.presentation.costs.CostsViewModel
import com.andrii.costsmanager.presentation.costs.CostsViewModelImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/*
 * Created by Andrii Medvid on 9/21/2019.
 */

val appModule = module {
    factory { CategoryDataBase.getInstance(androidContext()).categoryDao() }
    single<CategoryRepository> { CategoryLocalRepository(get()) }
    viewModel<CostsViewModel> { CostsViewModelImpl(get()) }
}
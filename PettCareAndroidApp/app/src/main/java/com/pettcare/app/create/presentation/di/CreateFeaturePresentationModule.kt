package com.pettcare.app.create.presentation.di

import com.pettcare.app.create.presentation.choosewhattocreate.ChooseWhatToCreateViewModel
import com.pettcare.app.create.presentation.createpost.CreatePostViewModel
import com.pettcare.app.create.presentation.picklocation.PickLocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createFeaturePresentationModule = module {

    viewModel { ChooseWhatToCreateViewModel(get()) }
    viewModel { parametersHolder -> CreatePostViewModel(parametersHolder.get(), get(), get(), get()) }
    viewModel { PickLocationViewModel(get(), get()) }
}

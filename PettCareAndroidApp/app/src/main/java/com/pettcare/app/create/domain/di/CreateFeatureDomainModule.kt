package com.pettcare.app.create.domain.di

import com.pettcare.app.create.domain.usecase.CreateCarePost
import com.pettcare.app.create.domain.usecase.CreateSocialPost
import com.pettcare.app.create.domain.usecase.PickLocationFromQuery
import org.koin.dsl.module

val createFeatureDomainModule = module {

    factory { CreateCarePost(get()) }
    factory { CreateSocialPost(get()) }
    factory { PickLocationFromQuery(get()) }
}

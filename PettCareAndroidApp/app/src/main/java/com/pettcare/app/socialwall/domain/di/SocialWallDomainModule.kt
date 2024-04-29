package com.pettcare.app.socialwall.domain.di

import com.pettcare.app.socialwall.domain.usecase.GetSocialWallPost
import com.pettcare.app.socialwall.domain.usecase.LikeSocialPost
import com.pettcare.app.socialwall.domain.usecase.PostSocialPostComment
import org.koin.dsl.module

val socialWallDomainModule = module {
    factory { GetSocialWallPost(get()) }
    factory { LikeSocialPost(get()) }
    factory { PostSocialPostComment(get()) }
}

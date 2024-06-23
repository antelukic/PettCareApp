package com.pettcare.app.extensions

import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.loadingOnStart() =
    this.onStart { BaseResponse.Loading }

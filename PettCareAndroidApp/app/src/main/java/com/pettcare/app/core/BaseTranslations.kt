package com.pettcare.app.core

import android.content.Context

abstract class BaseTranslations(context: Context) {

    protected val resources by lazy {
        context.resources
    }
}

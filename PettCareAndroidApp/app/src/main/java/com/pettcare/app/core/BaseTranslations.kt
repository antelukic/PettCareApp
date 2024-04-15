package com.pettcare.app.core

import android.content.Context
import android.content.res.Resources

abstract class BaseTranslations(context: Context) {

    protected val resources: Resources by lazy {
        context.resources
    }
}

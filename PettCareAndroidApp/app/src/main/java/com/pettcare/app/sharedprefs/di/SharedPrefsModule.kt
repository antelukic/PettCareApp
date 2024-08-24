package com.pettcare.app.sharedprefs.di

import com.pettcare.app.sharedprefs.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPrefsModule = module {
    single { SharedPreferences(androidContext()) }
}

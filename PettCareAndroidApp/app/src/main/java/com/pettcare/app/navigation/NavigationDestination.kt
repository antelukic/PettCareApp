package com.pettcare.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface NavigationDestination {

    val destination: String

    @Composable
    fun Screen(modifier: Modifier)
}

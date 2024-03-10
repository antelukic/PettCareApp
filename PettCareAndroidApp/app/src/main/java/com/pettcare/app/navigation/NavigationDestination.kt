package com.pettcare.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument

interface NavigationDestination {

    val arguments: List<NamedNavArgument>

    val destination: String

    @Composable
    fun Screen(modifier: Modifier)
}

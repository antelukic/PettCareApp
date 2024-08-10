package com.pettcare.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.places.api.Places
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.pettcare.app.bottomnav.BottomNavItem
import com.pettcare.app.bottomnav.BottomNavItemSelectedObserver
import com.pettcare.app.bottomnav.BottomNavSelectedPublisher
import com.pettcare.app.bottomnav.BottomNavigation
import com.pettcare.app.navigation.NavigationComponent
import com.pettcare.app.ui.theme.MyApplicationTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enablePlacesApi()
        Firebase.initialize(context = this)
        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance(),
        )
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBar()
                    },
                ) { paddingValues ->
                    val navController = rememberNavController()
                    NavigationComponent(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .background(MaterialTheme.colorScheme.background),
                    )
                }
            }
        }
    }

    private fun enablePlacesApi() {
        val apiKey = BuildConfig.MAPS_API_KEY

        if (apiKey.isEmpty()) {
            finish()
            return
        }

        Places.initializeWithNewPlacesApiEnabled(applicationContext, apiKey)
    }
}

@Composable
private fun BottomBar() {
    val bottomNavItemPublisher: BottomNavSelectedPublisher = koinInject()
    val bottomNavItemSelectedObserver: BottomNavItemSelectedObserver = koinInject()
    val bottomNavItemSelected = bottomNavItemSelectedObserver.observe().collectAsState(null).value
    AnimatedVisibility(visible = bottomNavItemSelected != null) {
        BottomNavigation(
            bottomNavItemSelected = bottomNavItemSelected ?: BottomNavItem.HOME,
            onBottomNavItemSelected = { bottomNavItem ->
                bottomNavItemPublisher.publish(bottomNavItem)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.spacing_4),
                    end = dimensionResource(id = R.dimen.spacing_4),
                    bottom = dimensionResource(id = R.dimen.spacing_4),
                )
                .clip(MaterialTheme.shapes.extraLarge),
        )
    }
}

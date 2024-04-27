package com.pettcare.app.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import com.pettcare.app.home.presentation.cluster.MapsMarkerCluster
import com.pettcare.app.uicomponents.SpinningProgressBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

private const val DEFAULT_ZOOM = 10f

@Composable
fun MapScreen(
    carePosts: ImmutableList<MapsMarkerCluster>,
    modifier: Modifier = Modifier,
) {
    val cameraPositionState = rememberCameraPositionState()
    var isMapVisible by remember { mutableStateOf(false) }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true, mapType = MapType.HYBRID),
        onMapLoaded = {
            isMapVisible = true
        },
    ) {
        DefaultClustering(
            items = carePosts,
            cameraPositionState,
        )
    }
    AnimatedVisibility(visible = isMapVisible.not()) {
        SpinningProgressBar(modifier = Modifier.fillMaxSize())
    }
    RequestLocationPermission { latLng ->
        cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, DEFAULT_ZOOM)
    }
}

private const val MAP_CLUSTER_ZOOM = 3f

@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun DefaultClustering(items: ImmutableList<MapsMarkerCluster>, cameraState: CameraPositionState) {
    val coroutineScope = rememberCoroutineScope()
    Clustering(
        items = items,
        // Optional: Handle clicks on clusters, cluster items, and cluster item info windows
        onClusterClick = {
            coroutineScope.launch {
                cameraState.move(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition(
                            items.first().itemPosition,
                            MAP_CLUSTER_ZOOM,
                            0f,
                            0f,
                        ),
                    ),
                )
            }
            false
        },
        onClusterItemClick = {
            false
        },
        clusterItemContent = { mapsMarkerCluster ->
            val request =
                ImageRequest.Builder(LocalContext.current).data(mapsMarkerCluster.itemImage).allowHardware(false)
                    .build()
            AsyncImage(
                model = request,
                contentDescription = "Profile Image Map",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
            )
        },
    )
}

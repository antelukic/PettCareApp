package com.pettcare.app.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import com.pettcare.app.R
import com.pettcare.app.home.presentation.cluster.MapsMarkerCluster
import com.pettcare.app.uicomponents.Avatar
import com.pettcare.app.uicomponents.SpinningProgressBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

private const val DEFAULT_ZOOM = 10f

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapScreen(
    carePosts: ImmutableList<MapsMarkerCluster>,
    resolvedLocation: LatLng,
    onUserClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cameraPositionState = rememberCameraPositionState()
    var isMapVisible by remember { mutableStateOf(false) }
    var areClustersRendered by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<MapsMarkerCluster?>(null) }
    val context = LocalContext.current
    var clusterManager by remember { mutableStateOf<ClusterManager<MapsMarkerCluster>?>(null) }
    LaunchedEffect(resolvedLocation) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(resolvedLocation, DEFAULT_ZOOM)
    }
    Box {
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
                onClusterClick = {
                    selectedUser = it
                },
            )
            MapEffect(key1 = carePosts) { map ->
                if (clusterManager == null) {
                    clusterManager = ClusterManager<MapsMarkerCluster>(context, map)
                }
                clusterManager?.setOnClusterItemInfoWindowClickListener {
                    areClustersRendered = true
                }
            }
        }

        AnimatedVisibility(visible = selectedUser != null) {
            UserCard(
                name = selectedUser?.title.orEmpty(),
                image = selectedUser?.itemImage.orEmpty(),
                price = selectedUser?.itemSnippet.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(dimensionResource(id = R.dimen.spacing_2))
                    .clickable {
                        selectedUser?.id?.let {
                            onUserClicked(it)
                        }
                    },
            )
        }
    }
    AnimatedVisibility(visible = isMapVisible.not() && areClustersRendered.not()) {
        SpinningProgressBar(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun UserCard(
    name: String,
    image: String,
    price: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_2))) {
            Avatar(profilePhoto = image, name = name)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2)))
            Text(
                text = price,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

private const val MAP_CLUSTER_ZOOM = 3f

@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun DefaultClustering(
    items: ImmutableList<MapsMarkerCluster>,
    cameraState: CameraPositionState,
    onClusterClick: (MapsMarkerCluster) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
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
            onClusterClick(it)
            false
        },
        clusterItemContent = { mapsMarkerCluster ->
            val request =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(mapsMarkerCluster.itemImage)
                    .allowHardware(false)
                    .fallback(ContextCompat.getDrawable(context, R.drawable.ic_person))
                    .build()
            AsyncImage(
                model = request,
                contentDescription = "Profile Image Map",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_person),
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
            )
        },
    )
}

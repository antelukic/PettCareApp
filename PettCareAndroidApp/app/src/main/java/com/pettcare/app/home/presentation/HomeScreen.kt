package com.pettcare.app.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pettcare.app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val PAGE_COUNT = 2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val pagerState = rememberPagerState {
        PAGE_COUNT
    }
    val coroutineScope = rememberCoroutineScope()
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
    ) {
        Box(modifier = modifier) {
            if (pagerState.currentPage == 0) {
                ListPage(
                    profiles = uiState.profiles,
                    onProfileClicked = viewModel::onProfileClicked,
                    loadMore = viewModel::nextPage,
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                MapScreen(
                    modifier = Modifier.fillMaxSize(),
                    carePosts = uiState.markers,
                )
            }
            ToggleButton(
                pagerState = pagerState,
                coroutineScope = coroutineScope,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(dimensionResource(id = R.dimen.spacing_4)),
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToggleButton(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
        onClick = { pagerState.toggle(coroutineScope) },
        modifier = modifier,
    ) {
        Image(
            imageVector = btnToggleIcon(currentPage = pagerState.currentPage),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_default)),
        )
        Text(
            text = btnToggleText(currentPage = pagerState.currentPage),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.background,
        )
    }
}

@Composable
private fun btnToggleText(currentPage: Int) = if (currentPage == PAGE_COUNT - 1) {
    stringResource(id = R.string.home_screen_btn_toggle_list)
} else {
    stringResource(id = R.string.home_screen_btn_toggle_map)
}

@Composable
private fun btnToggleIcon(currentPage: Int) = if (currentPage == PAGE_COUNT - 1) {
    Icons.AutoMirrored.Filled.List
} else {
    Icons.Default.Map
}

@OptIn(ExperimentalFoundationApi::class)
private fun PagerState.toggle(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        if (currentPage == PAGE_COUNT - 1) {
            animateScrollToPage(0)
        } else {
            animateScrollToPage(1)
        }
    }
}

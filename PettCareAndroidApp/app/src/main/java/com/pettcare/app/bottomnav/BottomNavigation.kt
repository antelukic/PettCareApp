package com.pettcare.app.bottomnav

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Notes
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.pettcare.app.R

@Composable
fun BottomNavigation(
    bottomNavItemSelected: BottomNavItem,
    onBottomNavItemSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shadowElevation = dimensionResource(R.dimen.bottom_navigation_shadow_elevation),
        color = MaterialTheme.colorScheme.tertiaryContainer,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val bottomNavItemModifier = Modifier
                .clip(CircleShape)
                .weight(1f)
                .padding(vertical = dimensionResource(id = R.dimen.spacing_1))

            BottomNavItem(
                label = stringResource(id = R.string.home_label_bottom_nav),
                icon = Icons.Outlined.Home,
                isSelected = bottomNavItemSelected == BottomNavItem.HOME,
                modifier = bottomNavItemModifier
                    .clickable {
                        onBottomNavItemSelected(BottomNavItem.HOME)
                    },
            )

            BottomNavItem(
                label = stringResource(id = R.string.social_wall_label_bottom_nav),
                icon = Icons.AutoMirrored.Outlined.Notes,
                isSelected = bottomNavItemSelected == BottomNavItem.SOCIAL_WALL,
                modifier = bottomNavItemModifier
                    .clickable {
                        onBottomNavItemSelected(BottomNavItem.SOCIAL_WALL)
                    },
            )

            BottomNavItem(
                label = stringResource(id = R.string.create_label_bottom_nav),
                icon = Icons.Outlined.Create,
                isSelected = bottomNavItemSelected == BottomNavItem.CREATE,
                modifier = bottomNavItemModifier
                    .clickable {
                        onBottomNavItemSelected(BottomNavItem.CREATE)
                    },
            )

            BottomNavItem(
                label = stringResource(id = R.string.chats_label_bottom_nav),
                icon = Icons.Outlined.ChatBubble,
                isSelected = bottomNavItemSelected == BottomNavItem.CHAT,
                modifier = bottomNavItemModifier
                    .clickable {
                        onBottomNavItemSelected(BottomNavItem.CHAT)
                    },
            )

            BottomNavItem(
                label = stringResource(id = R.string.profile_label_bottom_nav),
                icon = Icons.Outlined.PersonOutline,
                isSelected = bottomNavItemSelected == BottomNavItem.PROFILE,
                modifier = bottomNavItemModifier
                    .clickable {
                        onBottomNavItemSelected(BottomNavItem.PROFILE)
                    },
            )
        }
    }
}

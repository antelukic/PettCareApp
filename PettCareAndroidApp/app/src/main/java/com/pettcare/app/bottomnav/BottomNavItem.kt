package com.pettcare.app.bottomnav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.pettcare.app.R

enum class BottomNavItem {
    HOME, SOCIAL_WALL, CREATE, CHAT, PROFILE
}

@Composable
fun BottomNavItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    selectedItemColor: Color = MaterialTheme.colorScheme.onSecondary,
    notSelectedItemColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
) {
    val itemColor = if (isSelected) {
        selectedItemColor
    } else {
        notSelectedItemColor
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "${stringResource(id = R.string.bottom_nav_icon)} +  $label",
            tint = itemColor,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_1)))

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = itemColor,
        )
    }
}

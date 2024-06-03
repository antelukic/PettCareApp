package com.pettcare.app.create.presentation.createpost

import android.net.Uri
import com.pettcare.app.create.presentation.PostType
import com.pettcare.app.extensions.EMPTY

data class CreatePostUiState(
    val postType: PostType = PostType.CARE,
    val text: String = EMPTY,
    val photo: Uri? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val address: String? = if (postType == PostType.CARE) EMPTY else null,
    val price: String = EMPTY,
    val showAddressBottomSheet: Boolean = false,
    val isPostButtonEnabled: Boolean = false,
)

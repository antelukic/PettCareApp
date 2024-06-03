package com.pettcare.app.create.presentation.choosewhattocreate

import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.create.presentation.PostType
import com.pettcare.app.navigation.Router

class ChooseWhatToCreateViewModel(router: Router) : BaseViewModel<Unit>(router, Unit) {

    fun onPicked(postType: PostType) = publishNavigationAction { it.createPost(postType.id) }
}

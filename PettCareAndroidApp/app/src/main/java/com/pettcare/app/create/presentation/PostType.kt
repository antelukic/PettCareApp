package com.pettcare.app.create.presentation

enum class PostType(val id: String) {
    CARE("CARE"), SOCIAL("SOCIAL");

    companion object {
        fun fromId(id: String?) = when (id) {
            SOCIAL.id -> SOCIAL
            else -> CARE
        }
    }
}

package com.nanaka.status.models

data class User(
    var id: Int? = null,
    var username: String,
    var profilePicture: String?,
    var status: Status?,
    var isFollowed: Boolean = false,
    var followedLen: Int? = null,
    var followerLen: Int? = null,
)

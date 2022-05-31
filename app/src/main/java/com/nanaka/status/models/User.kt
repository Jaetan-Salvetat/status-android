package com.nanaka.status.models

data class User(
    var username: String,
    var profilePicture: String?,
    var status: Status?,
    var followedLen: Int? = null,
    var followerLen: Int? = null,
)

package com.dradinc.madmeditation.model

import org.json.JSONObject


data class User(
    var id: String,
    var email: String,
    var nickName: String,
    var avatar: String,
    var token: String
) {
    override fun toString(): String {
        return "User(id='$id', email='$email', nickName='$nickName', avatar='$avatar', token='$token')"
    }
}
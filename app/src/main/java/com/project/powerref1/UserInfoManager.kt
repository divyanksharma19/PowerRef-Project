package com.project.powerref1

import android.content.Context
import android.content.SharedPreferences

object UserInfoManager {
    var token: String? = null
    var userId: String? = null
    var email: String? = null
    var firstname: String? = null
    var lastname: String? = null

    fun loadUserInfo(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("PowerRefPrefs", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("token", null)
        userId = sharedPreferences.getString("userId", null)
        email = sharedPreferences.getString("email", null)
        firstname = sharedPreferences.getString("firstname", null)
        lastname = sharedPreferences.getString("lastname", null)
    }
}

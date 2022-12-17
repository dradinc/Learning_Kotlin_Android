package com.dradinc.madmeditation.common

import com.dradinc.madmeditation.db.DbManager
import com.dradinc.madmeditation.model.Mood
import com.dradinc.madmeditation.model.Quotes
import com.dradinc.madmeditation.model.User
import org.json.JSONArray
import org.json.JSONObject

class Global {
    companion object {
        val base_usrl = "http://mskko2021.mad.hakta.pro/api"
        var userData = User("","","","","")
        var moodsList = ArrayList<Mood>()
        var quotesList = ArrayList<Quotes>()

        // База данных (локальная)
        const val DATABASES_NAME = "Meditation"
        const val DATABASES_VERSION = 1

        // Заолнение данных пользователя
        fun newUserData(json: JSONObject){
            userData.id = json.getString("id")
            userData.email = json.getString("email")
            userData.nickName = json.getString("nickName")
            userData.avatar = json.getString("avatar")
            userData.token = json.getString("token")
        }

        // Заполнение данных настроения
        fun newMoodData(json: JSONArray) {
            moodsList.clear() // Временная мера
            for (i in 0 until  json.length())  {
                moodsList.add(
                    Mood(
                        json.getJSONObject(i).getInt("id"),
                        json.getJSONObject(i).getString("title"),
                        json.getJSONObject(i).getString("image"),
                        json.getJSONObject(i).getInt("position")
                    )
                )
            }
        }

        // Заполнение данных что-то
        fun newQuotesData(json: JSONArray) {
            quotesList.clear() // Временная мера
            for (i in 0 until  json.length())  {
                quotesList.add(
                    Quotes(
                        json.getJSONObject(i).getInt("id"),
                        json.getJSONObject(i).getString("title"),
                        json.getJSONObject(i).getString("image"),
                        json.getJSONObject(i).getString("description")
                    )
                )
            }
        }
    }
}

package com.dradinc.madmeditation.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.fragment.app.FragmentActivity
import com.dradinc.madmeditation.common.Global
import org.json.JSONObject

class DbManager(context: Context) : UserData.UserTable {

    // Наш помощник в открытии таблицы
    private val dbHelper = DbHelper(context)
    private lateinit var db: SQLiteDatabase

    fun dbOpen() { db = dbHelper.writableDatabase } // Открывает нашу базу данных и позволяет с ней взаимодействовать
    fun dbClose() { dbHelper.close() } // Закрывает соединение с базой данных

    // Работа с пользователем
    override fun authUser(
        id: String,
        email: String,
        password: String,
        nickname: String,
        avatar: String,
        token: String
    ) {
        db.insert(UserData.TABLE_USER, null, ContentValues().apply {
            put(UserData.COLUMN_ID, id)
            put(UserData.COLUMN_EMAIL, email)
            put(UserData.COLUMN_PASSWORD, password)
            put(UserData.COLUMN_NICKNAME, nickname)
            put(UserData.COLUMN_AVATAR, avatar)
            put(UserData.COLUMN_TOKEN, token)
        })
    }

    override fun selectUser() {

    }

    override fun checkAuthUser(): Boolean {
        var result = false

        val cursor = db.query(UserData.TABLE_USER, null, null, null, null, null, null, null)
        if (cursor.count > 0) result = true
        println(cursor.count)
        while (cursor?.moveToNext()!!)
            Global.newUserData(
                JSONObject()
                    .put("id", cursor.getString(cursor.getColumnIndex(UserData.COLUMN_ID).toInt()))
                    .put("email", cursor.getString(cursor.getColumnIndex(UserData.COLUMN_EMAIL).toInt()))
                    .put("nickName", cursor.getString(cursor.getColumnIndex(UserData.COLUMN_NICKNAME).toInt()))
                    .put("avatar", cursor.getString(cursor.getColumnIndex(UserData.COLUMN_AVATAR).toInt()))
                    .put("token", cursor.getString(cursor.getColumnIndex(UserData.COLUMN_TOKEN).toInt()))
        )
        cursor.close()

        return result
    }

    override fun deleteUser(id: String) {
        val selection = "${UserData.COLUMN_ID} LIKE ?"
        val selectionArgument = arrayOf(Global.userData.id)
        db.delete(UserData.TABLE_USER, selection, selectionArgument)
    }
}
package com.dradinc.madmeditation.db

import android.provider.BaseColumns

object UserData : BaseColumns {

    const val TABLE_USER = "user" // Наименование таблицы

    // Столбцы таблицы
    const val COLUMN_ID = "id" // Идентификатор
    const val COLUMN_EMAIL = "email" // Почта/логин
    const val COLUMN_PASSWORD = "password" // Пароль
    const val COLUMN_NICKNAME = "nickName" // Имя пользователя
    const val COLUMN_AVATAR = "avatar" // Аватарка пользователя
    const val COLUMN_TOKEN = "token" // Токен пользователя

    // Скрипты таблицы
    // Создание таблицы
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_USER (" +
            "$COLUMN_ID TEXT PRIMARY KEY," +
            "$COLUMN_EMAIL TEXT," +
            "$COLUMN_PASSWORD TEXT," +
            "$COLUMN_NICKNAME TEXT," +
            "$COLUMN_AVATAR TEXT," +
            "$COLUMN_TOKEN TEXT" +
            ")"

    // Удаление таблицы
    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    // Удаление пользователя
    //const val DELETE_USER = ""

    interface UserTable {
        fun selectUser()
        fun checkAuthUser() : Boolean
        fun authUser(id: String, email: String, password: String, nickname: String, avatar: String, token: String)
        fun deleteUser(id: String)
    }
}
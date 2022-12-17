package com.dradinc.madmeditation.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dradinc.madmeditation.common.Global

class DbHelper (context: Context) : SQLiteOpenHelper (context, Global.DATABASES_NAME, null, Global.DATABASES_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        // Создаём наши таблицы
        db?.execSQL(UserData.CREATE_TABLE)
    }

    // Срабатывает если поменять номер версии
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // Удаляем таблицы для обновления
        db?.execSQL(UserData.DROP_TABLE)

        // Снова создаём наши таблицы
        onCreate(db)
    }

}
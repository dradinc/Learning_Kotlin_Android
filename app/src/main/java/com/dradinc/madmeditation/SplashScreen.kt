package com.dradinc.madmeditation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dradinc.madmeditation.db.DbManager

class SplashScreen : AppCompatActivity() {
    private val db = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        db.dbOpen()
        val res = db.checkAuthUser()
        db.dbClose()
        if (res) {
            println("Пользователь авторизлван")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            println("Пользователь НЕ авторизован")
            val intent = Intent(this, Onboarding::class.java)
            startActivity(intent)
            finish()
        }

    }
}
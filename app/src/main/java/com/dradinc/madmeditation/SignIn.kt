package com.dradinc.madmeditation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dradinc.madmeditation.common.Global
import com.dradinc.madmeditation.databinding.ActivitySignInBinding
import com.dradinc.madmeditation.db.DbManager
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONObject

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    private val base_usrl = "http://mskko2021.mad.hakta.pro/api"
    private var client = OkHttpClient()
    val db = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.authBtn.setOnClickListener {
            if (binding.loginLable.text.isEmpty()) binding.loginLable.error = "Пустое поле"
            if (binding.passwordLable.text.isEmpty()) binding.passwordLable.error = "Пустое поле"

            if (binding.loginLable.text.isNotEmpty() and binding.passwordLable.text.isNotEmpty()) {
                // Строим тело запроса
                val body = ("{" +
                        "\"email\": \"${binding.loginLable.text}\"," +
                        "\"password\": \"${binding.passwordLable.text}\"" +
                        "}").toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                // Строим сам запрос
                val request = Request.Builder()
                    .url("$base_usrl/user/login")
                    .post(body)
                    .build()

                // Делаем запрос
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println("\n" +
                                e.toString() +
                                "\n")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        println(response.code)
                        if (response.code == 200) {
                            val json = JSONObject(response.body.string())
                            Global.newUserData(json)

                            db.dbOpen()
                            db.authUser(
                                Global.userData.id,
                                Global.userData.email,
                                binding.passwordLable.text.toString(),
                                Global.userData.nickName,
                                Global.userData.avatar,
                                Global.userData.token
                            )
                            db.dbClose()

                            val intent = Intent(this@SignIn, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                })
            }
        }
    }
}
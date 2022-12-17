package com.dradinc.madmeditation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dradinc.madmeditation.adapter.MoodsAdapter
import com.dradinc.madmeditation.adapter.QuotesAdapter
import com.dradinc.madmeditation.common.Global
import com.dradinc.madmeditation.databinding.FragmentMainBinding
import com.dradinc.madmeditation.model.Mood
import com.dradinc.madmeditation.model.Quotes
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.json.JSONObject

class MainFragment : Fragment(), MoodsAdapter.Listner, QuotesAdapter.Listner {
    // Наши переменные
    private lateinit var binding: FragmentMainBinding
    private val base_usrl = "http://mskko2021.mad.hakta.pro/api"
    private var client = OkHttpClient()
    private var moodAdapter = MoodsAdapter(this)
    private var quotesAdapter = QuotesAdapter(this)
    var callbackFeeling: (()->Unit)?=null // Для обратного вызова
    var callbackQuotes: (()->Unit)?=null // Для обратного вызова

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Указываем код, который мы хотим потом выполнить (обязательно до обрытного вызова)
        callbackFeeling = {
            binding.moodList.adapter = moodAdapter
        }
        callbackQuotes = {
            println(">>>>>>>>>>>>>>>>>>>>>> POEHALY")
            binding.infoBlock.adapter = quotesAdapter
        }

        // Данные пользователя
        binding.usernameTextview.text = Global.userData.nickName.plus('!')
        Picasso.get()
            .load(Global.userData.avatar)
            .into(binding.profileBtn)

        // Ощущения
        val requestMood = Request.Builder().url("${base_usrl}/feelings").build()
        client.newCall(requestMood).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) { }
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    // Получаем наш запрос
                    val json = JSONObject(response.body.string())
                    // Получаем список данных
                    val jsonList = json.getJSONArray("data")
                    // Передаём для заполнения
                    Global.newMoodData(jsonList)
                    Handler(Looper.getMainLooper()).post {
                        // Совершаем обратный вызов
                        callbackFeeling!!.invoke()
                    }
                }
            }
        })

        // Остальное
        val requestQuotes = Request.Builder().url("${base_usrl}/quotes").build()
        client.newCall(requestQuotes).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) { }
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    // Получаем наш запрос
                    val json = JSONObject(response.body.string())
                    // Получаем список данных
                    val jsonList = json.getJSONArray("data")
                    // Передаём для заполнения
                    Global.newQuotesData(jsonList)
                    Handler(Looper.getMainLooper()).post {
                        // Совершаем обратный вызов
                        println(">>>>>>>>>>>>>>>>>>>>>> POEHALY")
                        callbackQuotes!!.invoke()
                    }
                }
            }
        })
    }

    // Метод интерфейса адаптера, в котором мы и описываем что будет происходить
    override fun onClickMood(mood: Mood) {
        Toast.makeText(activity, mood.title, Toast.LENGTH_SHORT).show()
    }

    override fun onClickQuotes(quotes: Quotes) {
        Toast.makeText(activity, quotes.title, Toast.LENGTH_SHORT).show()
    }
}

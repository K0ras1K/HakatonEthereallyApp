package ru.k0ras1k.ethereally

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.k0ras1k.ethereally.activities.AuthChoiseActivity
import ru.k0ras1k.ethereally.activities.Intro0
import ru.k0ras1k.ethereally.activities.Intro1
import ru.k0ras1k.ethereally.api.http.AuthRequester
import ru.k0ras1k.ethereally.api.http.Requester

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var token: String

        val gson: Gson = GsonBuilder().create()

        val auth_requester = AuthRequester()

        val requester = Requester()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val token = getToken()

        if (token.isNullOrEmpty()) {
            // Токена нет, перенаправляем на экран авторизации/регистрации
            val intent = Intent(this, Intro0::class.java)
            startActivity(intent)
            finish()
        } else {
            MainActivity.token = token
            // Токен есть, перенаправляем на главное меню
//            val intent = Intent(this, MainMenuActivity::class.java)
//            startActivity(intent)
//            finish()
        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            deleteToken(this)
        }

    }

    fun getToken(): String? {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("jwt_token", null)
    }

    fun deleteToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove("jwt_token") // Удаляем токен
            apply()
        }
    }
}
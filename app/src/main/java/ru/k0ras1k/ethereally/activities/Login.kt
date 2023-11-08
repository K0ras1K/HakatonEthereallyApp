package ru.k0ras1k.ethereally.activities

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.k0ras1k.ethereally.ApplicationConstants
import ru.k0ras1k.ethereally.MainActivity
import ru.k0ras1k.ethereally.R
import ru.k0ras1k.ethereally.api.types.LoginRequest
import ru.k0ras1k.ethereally.api.types.LoginResponse

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val auth_button = findViewById<ImageButton>(R.id.button)
        auth_button.setOnClickListener {

            val email = findViewById<EditText>(R.id.email).text
            val password = findViewById<EditText>(R.id.password).text
            println(email.toString())
            println(password.toString())


            GlobalScope.launch(Dispatchers.IO) {
                val token = login(email.toString(), password.toString())

                withContext(Dispatchers.Main) { // Возврат в главный поток для обновления UI
                    println(token)
                    val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("jwt_token", token)
                    editor.apply()
                }
            }



//            val intent = Intent(this, AuthChoiseActivity::class.java)
//            startActivity(intent)
//            finish()
        }
    }


    suspend fun login(email: String, password: String): String {
        val response = MainActivity.requester.post(
            "${ApplicationConstants.API_URL}/public/users/login",
            LoginRequest(email, password)
        ).getOrThrow<LoginResponse>() // Получаем LoginResponse с токеном
        return response.token // Возвращаем токен
    }
}
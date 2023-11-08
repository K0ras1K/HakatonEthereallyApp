package ru.k0ras1k.ethereally.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.k0ras1k.ethereally.ApplicationConstants
import ru.k0ras1k.ethereally.MainActivity
import ru.k0ras1k.ethereally.R
import ru.k0ras1k.ethereally.api.types.LoginResponse
import ru.k0ras1k.ethereally.api.types.RegisterRequest
import ru.k0ras1k.ethereally.api.types.RegisterResponse

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val register_button = findViewById<ImageButton>(R.id.register_button)
        register_button.setOnClickListener {

            val email = findViewById<EditText>(R.id.email).text
            val number = findViewById<EditText>(R.id.number).text
            val first_name = findViewById<EditText>(R.id.first_name).text
            val last_name = findViewById<EditText>(R.id.last_name).text
            val password = findViewById<EditText>(R.id.password).text

            GlobalScope.launch(Dispatchers.IO) {
                val token = register(email.toString(), number.toString(), first_name.toString(), last_name.toString(), password.toString())

                withContext(Dispatchers.Main) { // Возврат в главный поток для обновления UI
                    println(token)
                    val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
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


    suspend fun register(email: String, number: String, first_name: String, last_name: String, password: String): String {
        val response = MainActivity.requester.post(
            "${ApplicationConstants.API_URL}/public/users/register",
            RegisterRequest(email, number, first_name, last_name, password)
        ).getOrThrow<RegisterResponse>() // Получаем RegisterResponse с токеном
        return response.token // Возвращаем токен
    }
}
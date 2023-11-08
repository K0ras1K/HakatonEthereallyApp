package ru.k0ras1k.ethereally.api.http

import okhttp3.Response
import ru.k0ras1k.ethereally.MainActivity
import java.io.IOException

class HttpHelper(val response: Response) {
    val body: String = response.body!!.string()

    inline fun <reified T>  getOrThrow(): T {
        if (!response.isSuccessful)
            throw IOException(response.toString())

        return MainActivity.gson.fromJson(body, T::class.java)
    }
}
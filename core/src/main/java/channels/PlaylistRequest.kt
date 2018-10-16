package channels

import okhttp3.OkHttpClient
import okhttp3.Request

class PlaylistRequest {
    companion object {
        fun get(url: String): String {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            return response.body()?.string() ?: ""
        }
    }
}
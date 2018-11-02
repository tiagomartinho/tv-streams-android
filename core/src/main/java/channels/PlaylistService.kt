package channels

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

interface PlaylistService {
    fun get(url: String, callback: (String)->(Unit))
}

class OkHttpPlaylistService : PlaylistService {
    override fun get(url: String, callback: (String)->(Unit)) {
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback("")
                }

                override fun onResponse(call: Call, response: Response) {
                    callback(response.body()?.string() ?: "")
                }
            })
        } catch(e:Exception) {
            callback("")
        }
    }
}
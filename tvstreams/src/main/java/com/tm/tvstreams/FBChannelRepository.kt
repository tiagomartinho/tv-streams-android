package com.tm.tvstreams

import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import channels.Channel
import channels.ChannelRepository

class FBChannelRepository: ChannelRepository {

    var db = FirebaseFirestore.getInstance()

    override fun channels(callback: (List<Channel>) -> Unit) {
        db.collection("users")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            callback(ArrayList())
                        }
                    } else {
                        Log.w("FBChannelRepository", "Error getting documents.", task.exception)
                    }
                }
    }

    override fun add(channel: Channel) {
        val channelMap = HashMap<String, Any>()
        channelMap["Name"] = channel.name
        db.collection("channels").add(channelMap)
    }
}

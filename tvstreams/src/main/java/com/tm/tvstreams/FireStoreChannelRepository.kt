package com.tm.tvstreams

import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import channels.Channel
import channels.ChannelRepository

class FireStoreChannelRepository: ChannelRepository {

    private var db = FirebaseFirestore.getInstance()
    private val userID = "1234"

    override fun channels(callback: (List<Channel>) -> Unit) {
        db.collection("Users")
                .document(userID)
                .collection("Channels")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            val channel = Channel(name = document.data["Name"] as String)
                            val channels = ArrayList<Channel>()
                            channels.add(channel)
                            callback(channels)
                        }
                    } else {
                        Log.w("FireStoreChannelRepository", "Error getting documents.", task.exception)
                    }
                }
    }

    override fun add(channel: Channel) {
        val channelMap = HashMap<String, Any>()
        channelMap["Name"] = channel.name
        db.collection("Users")
                .document(userID)
                .collection("Channels").add(channelMap).addOnSuccessListener {
                    print("addOnSuccessListener")
                }.addOnFailureListener {
                    print("addOnFailureListener")
                }
    }
}

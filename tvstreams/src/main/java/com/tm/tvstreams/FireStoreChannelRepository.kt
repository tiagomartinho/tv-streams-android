package com.tm.tvstreams

import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import channels.Channel
import channels.ChannelRepository
import com.google.firebase.firestore.CollectionReference

class FireStoreChannelRepository(private val userID: String) : ChannelRepository {

    private var db = FirebaseFirestore.getInstance()

    override fun channels(callback: (List<Channel>) -> Unit) {
        channelsCollection()
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val channels = ArrayList<Channel>()
                        for (document in task.result) {
                            val channel = Channel(name = document.data["Name"] as String)
                            channels.add(channel)
                        }
                        callback(channels)
                    } else {
                        Log.w("FSChannelRepository", "Error getting documents.", task.exception)
                    }
                }
    }

    override fun add(channel: Channel) {
        val channelMap = HashMap<String, Any>()
        channelMap["Name"] = channel.name
        channelsCollection()
                .add(channelMap)
                .addOnSuccessListener {
                    print("addOnSuccessListener")
                }.addOnFailureListener {
                    print("addOnFailureListener")
                }
    }

    private fun channelsCollection(): CollectionReference {
        return db.collection("Users")
                .document(userID)
                .collection("Channels")
    }

    override fun delete(channel: Channel) {
    }
}

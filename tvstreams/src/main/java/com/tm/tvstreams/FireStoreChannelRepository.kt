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
                .addOnSuccessListener {
                    val channels = ArrayList<Channel>()
                    for (document in it.documents) {
                        val channel = Channel(name = document.data?.get("Name") as String)
                        channels.add(channel)
                    }
                    callback(channels)
                }
                .addOnFailureListener {
                    callback(ArrayList())
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

    override fun deleteAll(callback: (Boolean) -> Unit) {
        channelsCollection()
                .get()
                .addOnSuccessListener {
                    it.documents.forEach {
                        it.reference.delete()
                    }
                    callback(true)
                }.addOnFailureListener {
                    callback(false)
                }
    }
}

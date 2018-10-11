package com.tm.tvstreams

import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import channels.Channel
import channels.ChannelRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot

class FireStoreChannelRepository(private val userID: String) : ChannelRepository {

    private var db = FirebaseFirestore.getInstance()

    override fun channels(callback: (List<Channel>) -> Unit) {
        channelsDocuments {
            callback(it.map { Channel(name = it.data?.get("Name") as String) })
        }
    }

    override fun add(channel: Channel) {
        val channelMap = HashMap<String, Any>()
        channelMap["Name"] = channel.name
        channelsCollection().add(channelMap)
    }

    override fun delete(channel: Channel, callback: (Boolean) -> Unit) {
        channelsDocuments {
            it.forEach {
                val name = it.data?.get("Name") as String
                if (name == channel.name) {
                    it.reference.delete()
                }
            }
            callback(true)
        }
    }

    override fun deleteAll(callback: (Boolean) -> Unit) {
        channelsDocuments {
            it.forEach {
                it.reference.delete()
            }
            callback(true)
        }
    }

    private fun channelsCollection(): CollectionReference {
        return db.collection("Users")
                .document(userID)
                .collection("Channels")
    }

    private fun channelsDocuments(callback: (List<DocumentSnapshot>) -> Unit) {
        channelsCollection()
                .get()
                .addOnSuccessListener {
                    callback(it.documents)
                }
                .addOnFailureListener {
                    callback(ArrayList())
                }
    }
}

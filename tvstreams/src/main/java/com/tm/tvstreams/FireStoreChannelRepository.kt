package com.tm.tvstreams

import com.google.firebase.firestore.FirebaseFirestore
import channels.Channel
import channels.ChannelRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot


class FireStoreChannelRepository(private val userID: String) : ChannelRepository {

    private var db = FirebaseFirestore.getInstance()

    override fun channels(callback: (List<Channel>) -> Unit) {
        channelsDocuments {
            callback(it.map { Channel(it.data) })
        }
    }

    override fun add(channels: List<Channel>, callback: (Boolean) -> Unit) {
        val batch = db.batch()
        channels.forEach {
            val document = channelsCollection().document()
            batch.set(document, it.map())
        }
        batch.commit()
                .addOnSuccessListener { callback(true) }
                .addOnFailureListener { callback(false) }
    }

    override fun add(channel: Channel, callback: (Boolean) -> Unit) {
        add(arrayListOf(channel)) { callback(it) }
    }

    override fun delete(channel: Channel, callback: (Boolean) -> Unit) {
        channelsDocuments {
            val batch = db.batch()
            it.forEach {
                if (Channel(it.data) == channel) {
                    batch.delete(it.reference)
                }
            }
            batch.commit()
            callback(true)
        }
    }

    override fun deleteAll(callback: (Boolean) -> Unit) {
        channelsDocuments {
            val batch = db.batch()
            it.forEach {
                batch.delete(it.reference)
            }
            batch.commit()
            callback(true)
        }
    }

    override fun update(channel: Channel, updatedChannel: Channel, callback: (Boolean) -> Unit) {
        channelsDocuments {
            val batch = db.batch()
            it.forEach {
                if (Channel(it.data) == channel) {
                    batch.update(it.reference,updatedChannel.map())
                }
            }
            batch.commit()
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

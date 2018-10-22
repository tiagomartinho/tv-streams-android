package com.tm.tvstreams

import channels.Channel
import channels.ChannelRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

class FBChannelRepository(private val userID: String) : ChannelRepository {

    private var db = FirebaseDatabase.getInstance()

    override fun addListener(callback: (List<Channel>) -> Unit) {
        reference().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val channels = dataSnapshot.children.mapNotNull { it.getValue(Channel::class.java) }
                callback(channels)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(arrayListOf())
            }
        })
    }

    override fun channels(callback: (List<Channel>) -> Unit) {
        reference().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val channels = dataSnapshot.children.mapNotNull { it.getValue(Channel::class.java) }
                callback(channels)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(arrayListOf())
            }
        })
    }

    override fun add(channels: List<Channel>, callback: (Boolean) -> Unit) {
        reference().setValue(channels) { _,_ -> callback(true) }
    }

    override fun add(channel: Channel, callback: (Boolean) -> Unit) {
        add(arrayListOf(channel)) { callback(it) }
    }

    override fun delete(channel: Channel, callback: (Boolean) -> Unit) {
    }

    override fun deleteAll(callback: (Boolean) -> Unit) {
        reference().removeValue()
    }

    override fun update(channel: Channel, updatedChannel: Channel, callback: (Boolean) -> Unit) {
    }

    private fun reference(): DatabaseReference {
        return db.getReference("users").child(userID).child("channels")
    }
}

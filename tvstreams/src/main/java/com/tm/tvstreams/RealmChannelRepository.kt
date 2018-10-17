package com.tm.tvstreams

import android.content.Context
import channels.Channel
import channels.ChannelRepository
import io.realm.Realm

class RealmChannelRepository(private val userID: String, context: Context) : ChannelRepository {

    companion object {
        private val instance = "tvstreams.us1a.cloud.realm.io"
        val authURL = "https://$instance/auth"
    }

    init {
        Realm.init(context)
    }

    override fun channels(callback: (List<Channel>) -> Unit) {
    }

    override fun add(channel: Channel, callback: (Boolean) -> Unit) {
    }

    override fun add(channels: List<Channel>, callback: (Boolean) -> Unit) {
    }

    override fun delete(channel: Channel, callback: (Boolean) -> Unit) {
    }

    override fun deleteAll(callback: (Boolean) -> Unit) {
    }

    override fun update(channel: Channel, updatedChannel: Channel, callback: (Boolean) -> Unit) {
    }

    override fun addListener(callback: (Unit) -> Unit) {
    }
}
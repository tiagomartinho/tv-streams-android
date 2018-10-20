package com.tm.tvstreams

import android.content.Context
import channels.Channel
import channels.ChannelRepository
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

public open class ChannelRealm (
    @PrimaryKey
    @Required
    val name: String,
    @Required
    val link: String,
    @Required
    val source: String,
    @Required
    val metadata: String
): RealmObject()

class RealmChannelRepository : ChannelRepository {

    companion object {
        private const val instance = "tvstreams.us1a.cloud.realm.io"
        const val authURL = "https://$instance/auth"
    }

    private var realm = Realm.getDefaultInstance()

    override fun channels(callback: (List<Channel>) -> Unit) {
//        realm.executeTransactionAsync { realm ->
//            val results = realm.where(ChannelRealm::class.java).findAll()
//            val channels = results.map {
//                 Channel(it.source, it.metadata, it.name, it.link)
//            }
//            callback(channels)
//        }
        callback(arrayListOf())
    }

    override fun add(channel: Channel, callback: (Boolean) -> Unit) {
        realm.executeTransactionAsync { realm ->
            val item = ChannelRealm(channel.name, channel.link, channel.source, channel.metadata)
            realm.insert(item)
        }
    }

    override fun add(channels: List<Channel>, callback: (Boolean) -> Unit) {
        realm.executeTransactionAsync { realm ->
            val items = channels.map { ChannelRealm(it.name, it.link, it.source, it.metadata) }
            realm.insert(items)
        }
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
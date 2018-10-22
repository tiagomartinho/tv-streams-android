package channels

import com.tm.core.player.ChannelPlayer

class Channel(
    var source: String = "",
    var metadata: String = "",
    var name: String = "",
    var link: String = ""
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Channel
        if (source != other.source) return false
        if (metadata != other.metadata) return false
        if (name != other.name) return false
        if (link != other.link) return false
        return true
    }

    override fun hashCode(): Int {
        var result = source.hashCode()
        result = 31 * result + metadata.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + link.hashCode()
        return result
    }
}

fun Channel.channelPlayer(): ChannelPlayer {
    return ChannelPlayer(name, link)
}
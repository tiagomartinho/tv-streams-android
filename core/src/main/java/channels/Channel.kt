package channels

class Channel(val source: String, val metadata: String, val name: String, val link: String) {

    constructor(map: Map<String, Any>?) :
            this(map?.get("Source") as String,
            map?.get("Metadata") as String,
            map?.get("Name") as String,
            map?.get("Link") as String)

    fun map(): HashMap<String, Any> {
        val channelMap = HashMap<String, Any>()
        channelMap["Source"] = source
        channelMap["Metadata"] = metadata
        channelMap["Name"] = name
        channelMap["Link"] = link
        return channelMap
    }

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
package channels

interface ChannelRepository {
    fun channels(callback: (List<Channel>) -> Unit)
    fun add(channel: Channel)
    fun add(channels: List<Channel>)
    fun delete(channel: Channel, callback: (Boolean) -> Unit)
    fun deleteAll(callback: (Boolean) -> Unit)
}
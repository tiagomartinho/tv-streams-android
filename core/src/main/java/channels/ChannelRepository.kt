package channels

interface ChannelRepository {
    fun channels(callback: (List<Channel>) -> Unit)
    fun add(channel: Channel, callback: (Boolean) -> Unit)
    fun add(channels: List<Channel>, callback: (Boolean) -> Unit)
    fun delete(channel: Channel, callback: (Boolean) -> Unit)
    fun deleteAll(callback: (Boolean) -> Unit)
    fun update(channel: Channel, updatedChannel: Channel, callback: (Boolean) -> Unit)
    fun addListener(callback: (Unit) -> Unit)
}
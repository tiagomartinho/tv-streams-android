package channels

interface ChannelRepository {
    fun channels(callback: (List<Channel>) -> Unit)
    fun add(channel: Channel)
    fun delete(channel: Channel)
    fun deleteAll(callback: (Boolean) -> Unit)
}
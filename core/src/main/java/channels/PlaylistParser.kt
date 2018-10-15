package channels

class PlaylistParser {
    companion object {
        fun parse(content: String): ArrayList<Channel> {
            var channels =  arrayListOf<Channel>()
            for(line in content.lines()) {
                if(NameParser.isChannel(line)) {
                    channels.add(Channel("a", "b", "c", "d"))
                }
            }
            return channels
        }
    }
}

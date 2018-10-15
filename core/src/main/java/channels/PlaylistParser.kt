package channels

class PlaylistParser {
    companion object {
        fun parse(content: String): ArrayList<Channel> {
            var channels =  arrayListOf<Channel>()
            var name: String? = null
            for(line in content.lines()) {
                if(NameParser.isChannel(line)) {
                    name = NameParser.extract(line)
                }
                if(LinkParser.isLinkValid(line)){
                    channels.add(Channel("a","b",name ?: line, line))
                    name = null
                }
            }
            return channels
        }
    }
}

package channels

class PlaylistParser {
    companion object {
        fun parse(source: String, content: String): ArrayList<Channel> {
            var channels = arrayListOf<Channel>()
            var name: String? = null
            for (line in content.lines()) {
                if (isCommaSeparated(line)) {
                    val splitLine = line.split(",")
                    val streamName = NameParser.extract(splitLine.first())
                    val link = LinkParser.extractLink(splitLine.last())
                    channels.add(Channel(source, "b", streamName, link))
                } else {
                    if (NameParser.isChannel(line)) {
                        name = NameParser.extract(line)
                    }
                    if (LinkParser.isLinkValid(line)) {
                        val link = LinkParser.extractLink(line)
                        channels.add(Channel(source, "b", name ?: link, link))
                        name = null
                    }
                }
            }
            return channels
        }

        private fun isCommaSeparated(line: String): Boolean {
            val splitLine = line.split(",")
            return splitLine.first() != null &&
                splitLine.last() != null &&
                splitLine.count() == 2 &&
                LinkParser.isLinkValid(splitLine.last()) &&
                !line.startsWith("#")
        }
    }
}

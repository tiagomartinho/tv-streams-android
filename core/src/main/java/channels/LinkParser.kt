package channels

class LinkParser {
    companion object {

        private val prefixes = arrayListOf("http://", "https://", "rtmp://", "rtmpe://", "rtmpt://", "rtmps://", "rtsp://", "mms://", "rtp://")

        fun isLinkValid(line: String): Boolean {
            return !prefixes.none { line.startsWith(it) }
        }

        fun extractLink(line: String): String {
            val suffix = "|X-Forwarded-For="
            if(line.contains(suffix)) {
                val index = line.indexOf(suffix)
                return line.removeRange(index, line.count())
            }
            return line
        }
    }
}

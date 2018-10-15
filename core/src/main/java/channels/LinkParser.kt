package channels

class LinkParser {
    companion object {

        private val prefixes = arrayListOf("http://", "https://", "rtmp://", "rtmpe://", "rtmpt://", "rtmps://", "rtsp://", "mms://", "rtp://")

        fun isLinkValid(line: String): Boolean {
            return !prefixes.none { line.startsWith(it) }
        }
    }
}

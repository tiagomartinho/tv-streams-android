package channels

class NameParser {
    companion object {
        fun isChannel(line: String): Boolean {
            return line.startsWith("#EXTINF")
        }

        fun extract(line: String): String {
            var name = line.split(",").last()
            while (hasSomethingToRemove(name)) {
                name = remove(name)
            }
            return name.trim()
        }

        private fun remove(name: String): String {
            val first = name.indexOf("[")
            val second = name.indexOf("]")
            return name.removeRange(first, second + 1)
        }

        private fun hasSomethingToRemove(name: String): Boolean {
            return name.contains("[") && name.contains("]") && name.indexOf("[") < name.indexOf("]")
        }
    }
}


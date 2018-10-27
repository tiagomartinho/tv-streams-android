package channels

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.Keep
import com.tm.core.player.ChannelPlayer

@Keep
class Channel(
    var source: String = "",
    var metadata: String = "",
    var name: String = "",
    var link: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(source)
        parcel.writeString(metadata)
        parcel.writeString(name)
        parcel.writeString(link)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Channel> {
        override fun createFromParcel(parcel: Parcel): Channel {
            return Channel(parcel)
        }

        override fun newArray(size: Int): Array<Channel?> {
            return arrayOfNulls(size)
        }
    }
}

fun Channel.channelPlayer(): ChannelPlayer {
    return ChannelPlayer(name, link)
}
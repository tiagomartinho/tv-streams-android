package com.tm.core.player

import android.os.Parcel
import android.os.Parcelable

class ChannelPlayer(val name: String, val url: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChannelPlayer

        if (name != other.name) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + url.hashCode()
        return result
    }

    companion object CREATOR : Parcelable.Creator<ChannelPlayer> {
        override fun createFromParcel(parcel: Parcel): ChannelPlayer {
            return ChannelPlayer(parcel)
        }

        override fun newArray(size: Int): Array<ChannelPlayer?> {
            return arrayOfNulls(size)
        }
    }
}

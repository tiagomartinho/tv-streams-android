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

    companion object CREATOR : Parcelable.Creator<ChannelPlayer> {
        override fun createFromParcel(parcel: Parcel): ChannelPlayer {
            return ChannelPlayer(parcel)
        }

        override fun newArray(size: Int): Array<ChannelPlayer?> {
            return arrayOfNulls(size)
        }
    }
}

package com.tc.reading.entity

class PkVideo : PkEntity() {

    var videoSuitId: String = ""

    override fun toString(): String {
        return "Video(id = '$id' videoSuitId = '$videoSuitId' name='$name', file='$file', summary='$summary')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PkVideo
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
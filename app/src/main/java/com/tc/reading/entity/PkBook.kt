package com.tc.reading.entity

class PkBook : PkEntity() {
    var content: String = ""
    var details: String = ""
    var bookSuitId: String = ""
    var audios: MutableList<PkAudio> = mutableListOf()
    var videos: MutableList<PkVideo> = mutableListOf()

    override fun toString(): String {
        return "name: $name, file: $file"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PkBook
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
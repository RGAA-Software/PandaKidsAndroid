package com.tc.reading.entity

class Video {

    var name: String = ""
    var file: String = ""
    var summary: String = ""

    override fun toString(): String {
        return "Video(name='$name', file='$file', summary='$summary')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Video

        return file == other.file
    }

    override fun hashCode(): Int {
        return file.hashCode()
    }

}
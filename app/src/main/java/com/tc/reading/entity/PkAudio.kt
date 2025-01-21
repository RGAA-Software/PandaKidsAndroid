package com.tc.reading.entity

class PkAudio : PkEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PkAudio
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
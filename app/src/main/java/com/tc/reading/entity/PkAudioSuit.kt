package com.tc.reading.entity

class PkAudioSuit : PkEntity() {

    override fun toString(): String {
        return "name: $name"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PkAudioSuit
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
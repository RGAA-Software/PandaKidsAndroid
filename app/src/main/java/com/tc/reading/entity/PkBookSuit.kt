package com.tc.reading.entity

class PkBookSuit : PkEntity() {
    var content: String = ""
    var details: String = ""
    var bookSuitPath: String = ""
    var series: String = ""

    override fun toString(): String {
        return "name: $name, summary: $summary, id: $id"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PkBookSuit
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
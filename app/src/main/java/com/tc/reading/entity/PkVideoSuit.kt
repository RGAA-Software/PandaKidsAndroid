package com.tc.reading.entity

import java.io.Serializable

class PkVideoSuit : PkEntity() {
    var content: String = "";
    var details: String = "";
    var videoSuitPath: String = "";
    var series: String = "";

    override fun toString(): String {
        return "VideoSuit(id='$id', author='$author', cover='$cover', coverId='$coverId', name='$name', summary='$summary', file='$file', categories=$categories, grades=$grades, content='$content', details='$details', videoSuitPath='$videoSuitPath', series='$series')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PkVideoSuit

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
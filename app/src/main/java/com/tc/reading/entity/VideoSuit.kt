package com.tc.reading.entity

import java.io.Serializable

class VideoSuit : Serializable {
    var id: String = ""
    var author: String = "";
    var cover: String = "";
    var coverId: String = "";
    var name: String = "";
    var summary: String = "";
    var file: String = "";
    var categories: MutableList<String> = mutableListOf();
    var grades: MutableList<String> = mutableListOf();
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

        other as VideoSuit

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
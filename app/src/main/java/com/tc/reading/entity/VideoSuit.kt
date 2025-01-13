package com.tc.reading.entity

class VideoSuit {
    public var author: String = "";
    public var cover: String = "";
    public var coverId: String = "";
    public var name: String = "";
    public var summary: String = "";
    public var file: String = "";
    public var categories: MutableList<String> = mutableListOf();
    public var grades: MutableList<String> = mutableListOf();
    public var content: String = "";
    public var details: String = "";
    public var videoSuitPath: String = "";
    public var series: String = "";

    override fun toString(): String {
        return "VideoSuit(author='$author', cover='$cover', coverId='$coverId', name='$name', summary='$summary', file='$file', categories=$categories, grades=$grades, content='$content', details='$details', videoSuitPath='$videoSuitPath', series='$series')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VideoSuit

        return videoSuitPath == other.videoSuitPath
    }

    override fun hashCode(): Int {
        return videoSuitPath.hashCode()
    }

}
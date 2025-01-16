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
}
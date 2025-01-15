package com.tc.reading.entity

import java.io.Serializable

open class PkEntity : Serializable {
    var id: String = ""
    var author: String = "";
    var cover: String = "";
    var coverId: String = "";
    var name: String = "";
    var summary: String = "";
    var file: String = "";
    var categories: MutableList<String> = mutableListOf();
    var grades: MutableList<String> = mutableListOf();
}
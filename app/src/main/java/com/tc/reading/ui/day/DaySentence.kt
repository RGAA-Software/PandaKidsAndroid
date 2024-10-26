package com.tc.reading.ui.day

class DaySentence(public var type: String) {
    public var author: String = "";
    public var content: String = "";
    public var translation: String = "";
    public var imageUrl: String = "";
    public var from: String = "";

    override fun toString(): String {
        return "DaySentence(type='$type', author='$author', content='$content', translation='$translation', imageUrl='$imageUrl', from='$from')"
    }

}
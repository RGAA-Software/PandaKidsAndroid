package com.tc.reading.ui.labelfilter

class LabelFilterItem(var name: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as LabelFilterItem
        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
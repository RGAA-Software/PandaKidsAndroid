package com.tc.reading.res

class Api {

    companion object {
        // video suits
        val API_VIDEOSUIT_QUERY = "/pandakids/videosuit/query"
        val API_VIDEOSUIT_QUERY_LIKE_NAME = "/pandakids/videosuit/query/like/name"

        // recommend
        val API_TODAY_VIDEOSUITS = "/pandakids/recommend/today/videosuits"

        // videos
        val API_VIDEO_QUERY = "/pandakids/video/query"

        // const variables
        const val KEY_CODE = "Code"
        const val KEY_DATA = "Data"
        const val KEY_ID = "Id"
        const val KEY_NAME = "Name"
        const val KEY_FILE = "File"
        const val KEY_COVER = "Cover"
        const val KEY_COVER_ID = "CoverId"
        const val KEY_VIDEOSUITS = "VideoSuits"
        const val KEY_VIDEOSUIT_ID = "VideoSuitId"
        const val KEY_PAGE = "Page"
        const val KEY_PAGE_SIZE = "PageSize"
        const val KEY_VIDEOS = "Videos"
        const val KEY_SUMMARY = "Summary"
        const val KEY_CONTENT = "Content"
        const val KEY_DETAILS = "Details"
        const val KEY_CATEGORIES = "Categories"
        const val KEY_GRADES = "Grades"
    }

}
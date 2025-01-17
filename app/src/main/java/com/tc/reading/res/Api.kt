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

        // book suit
        val API_BOOKSUIT_QUERY = "/pandakids/booksuit/query"
        val API_BOOKSUIT_QUERY_LIKE_NAME = "/pandakids/booksuit/query/like/name"

        // book
        val API_BOOK_QUERY = "/pandakids/book/query"

        // audio suit
        val API_AUDIOSUIT_QUERY = "/pandakids/audiosuit/query"

        // audio
        val API_AUDIO_QUERY = "/pandakids/audio/query"
        val API_AUDIO_QUERY_LIKE_NAME = "/pandakids/audio/query/like/name"

        // const variables
        const val KEY_CODE = "Code"
        const val KEY_DATA = "Data"
        const val KEY_ID = "Id"
        const val KEY_NAME = "Name"
        const val KEY_AUTHOR = "Author"
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
        const val KEY_BOOKSUITS = "BookSuits"
        const val KEY_BOOKSUIT_PATH = "BookSuitPath"
        const val KEY_SERIES = "Series"
        const val KEY_BOOKS = "Books"
        const val KEY_AUDIOSUITS = "AudioSuits"
        const val KEY_AUDIOS = "Audios"
    }

}
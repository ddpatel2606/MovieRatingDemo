package com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant

const val HTTP_REQUEST_TIMEOUT = 30L
const val INTERCEPTOR_FOR_REQUEST = "interceptor_request"
const val INTERCEPTOR_FOR_CACHE = "interceptor_cache"
const val CACHE_SIZE = 1024 * 1024 * 10L // 10 MB
const val CACHE_MAX_STALE_TIME = 60 * 60 * 24 * 2 // 2 days
const val CACHE_MAX_AGE = 30 * 60  // 30 min

const val LIST_ID_POPULAR = "popular"
const val LIST_ID_TOP_RATED = "top_rated"

const val INTENT_TYPE = "intent_type"
const val MEDIA_TYPE = "media_type"
const val TITLE = "title"
const val DETAIL_ID = "id"
const val LIST_ID = "list_id"
const val LIST = "list"

const val SEASON_NUMBER = "season_number"

const val ILLEGAL_ARGUMENT_MEDIA_TYPE = "Invalid MediaType"
const val ILLEGAL_ARGUMENT_FRAGMENT_TYPE = "Invalid Fragment type"
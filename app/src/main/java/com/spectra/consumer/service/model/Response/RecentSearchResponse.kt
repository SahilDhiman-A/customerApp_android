package com.spectra.consumer.service.model.Response

import java.util.*

data class RecentSearchResponse(val data: RecentSearch?, val message: String, val statusCode: Int, val additionalInfo: String)



//
//{
//    "data": {
//    "search_info": [
//    "home screen",
//    "home",
//    "Speed",
//    "data",
//    "my account"
//    ],
//    "is_active": true,
//    "_id": "6103e764befbad980301771c",
//    "can_id": "9071512",
//    "created_at": "2021-07-30T12:46:59.864Z",
//    "updated_at": "2021-07-30T12:46:59.864Z"
//},
//    "message": "Search Keyword List!!",
//    " ": 200,
//    "additionalInfo": null
//}
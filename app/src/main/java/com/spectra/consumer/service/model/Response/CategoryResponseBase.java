package com.spectra.consumer.service.model.Response;

import java.util.List;

public class CategoryResponseBase {
    public List<Category> data;
    public String message;
    public String statusCode;
    public String additionalInfo;
}


//{
//        "data": [
//        {
//        "_id": "60f6602a04dd893488aece5b",
//        "name": "Top up",
//        "is_deleted": false,
//        "is_active": true,
//        "created_at": "2021-07-20T05:33:30.588Z",
//        "updated_at": "2021-07-20T05:33:30.589Z",
//        "__v": 0,
//        "category_info": {
//        "_id": "60efc5f94b27974e0cc16372",
//        "name": "Category 1",
//        "is_deleted": false,
//        "is_active": true,
//        "segment_id": "60f6602a04dd893488aece5b",
//        "created_at": "2021-07-15T05:22:01.507Z",
//        "updated_at": "2021-07-15T05:22:01.508Z",
//        "__v": 0
//        },
//        "faq_info": [
//        {
//        "_id": "60efcac02285535789a56f2e",
//        "question": "Test Question",
//        "answer": "Test Answer",
//        "thumbs_up_count": 0,
//        "is_deleted": false,
//        "is_active": true,
//        "category_id": "60efc5f94b27974e0cc16372",
//        "created_at": "2021-07-15T05:42:24.951Z",
//        "updated_at": "2021-07-15T05:42:24.951Z",
//        "__v": 0
//        }
//        ]
//        }
//        ],
//        "message": "FAQ Info!!",
//        "statusCode": 200,
//        "additionalInfo": null
//        }
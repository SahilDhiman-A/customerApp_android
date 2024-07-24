package com.spectra.consumer.service.model.Response

data class RecentSearch(val search_info: List<String>,
                        val is_active: Boolean,
                        val _id: String,
                        val can_id: String,
                        val created_at: String,
                        val updated_at: String)

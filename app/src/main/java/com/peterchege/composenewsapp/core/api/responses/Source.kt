package com.peterchege.composenewsapp.core.api.responses

import kotlinx.serialization.Serializable


@Serializable
data class Source(
    val id: String?,
    val name: String?
)
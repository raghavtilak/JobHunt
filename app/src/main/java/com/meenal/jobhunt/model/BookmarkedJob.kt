package com.meenal.jobhunt.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_jobs")
data class BookmarkedJob(
    @PrimaryKey val id: String,
    val title: String,
    val job_location_slug: String,
    val salary_max: Int?,
    val salary_min: Int?,
    val whatsapp_no: Long
)
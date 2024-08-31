package com.meenal.jobhunt.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Job(
    val id: String,
    val title: String,
    val job_location_slug: String,
    val salary_max: Int?,
    val salary_min: Int?,
    val whatsapp_no: Long
): Parcelable

package com.meenal.jobhunt.service

import com.meenal.jobhunt.model.Job
import com.meenal.jobhunt.model.JobResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JobApiService {
    @GET("common/jobs")
    suspend fun getJobs(@Query("page") page: Int): Response<JobResponse>
}
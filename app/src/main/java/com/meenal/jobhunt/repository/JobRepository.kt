package com.meenal.jobhunt.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.meenal.jobhunt.db.dao.JobDao
import com.meenal.jobhunt.model.BookmarkedJob
import com.meenal.jobhunt.model.Job
import com.meenal.jobhunt.service.JobApiService
import retrofit2.Response

class JobRepository(private val jobDao: JobDao, private val apiService: JobApiService) {

    val bookmarkedJobs: LiveData<List<BookmarkedJob>> = jobDao.getBookmarkedJobs()

    suspend fun getJobs(page: Int): List<Job>?{
        val a = apiService.getJobs(page).body()?.results
        Log.d("TAG","REPO RESULT= $a")
        return a
    }

    suspend fun bookmarkJob(job: Job) {
        jobDao.bookmarkJob(
            BookmarkedJob(
                id = job.id,
                title = job.title,
                job_location_slug = job.job_location_slug,
                salary_max = job.salary_max,
                salary_min = job.salary_min,
                whatsapp_no = job.whatsapp_no
            )
        )
    }
}

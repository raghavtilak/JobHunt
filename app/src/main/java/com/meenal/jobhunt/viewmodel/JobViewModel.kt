package com.meenal.jobhunt.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.meenal.jobhunt.db.JobDatabase
import com.meenal.jobhunt.model.BookmarkedJob
import com.meenal.jobhunt.model.Job
import com.meenal.jobhunt.network.RetrofitInstance
import com.meenal.jobhunt.repository.JobRepository
import kotlinx.coroutines.launch

class JobViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: JobRepository
    val bookmarkedJobs: LiveData<List<BookmarkedJob>>
    val jobs = MutableLiveData<List<Job>>()
    val isLoading = MutableLiveData<Boolean>(true)
    val error = MutableLiveData<String?>()

    private var currentPage = 1

    init {
        val jobDao = JobDatabase.getDatabase(application).jobDao()
        repository = JobRepository(jobDao, RetrofitInstance.api)
        bookmarkedJobs = repository.bookmarkedJobs
    }

    fun fetchJobs(page: Int=1) {
        Log.d("TAG","FETCH JOBS")
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = repository.getJobs(page)
                if (response!=null) {
                    jobs.value = response!!
                } else {
                    error.value = "Failed to load jobs"
                }
            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
            isLoading.value = false
        }
    }

    fun bookmarkJob(job: Job) {
        viewModelScope.launch {
            repository.bookmarkJob(job)
        }
    }

    fun loadMoreJobs() {
        if (!isLoading.value!!) {
            currentPage++
            fetchJobs(currentPage)
        }
    }
}

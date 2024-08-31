package com.meenal.jobhunt.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.meenal.jobhunt.model.BookmarkedJob

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bookmarkJob(job: BookmarkedJob)

    @Query("SELECT * FROM bookmarked_jobs")
    fun getBookmarkedJobs(): LiveData<List<BookmarkedJob>>
}
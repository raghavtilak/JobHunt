package com.meenal.jobhunt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meenal.jobhunt.databinding.ItemJobBinding
import com.meenal.jobhunt.model.BookmarkedJob

class BookmarkAdapter :
    ListAdapter<BookmarkedJob, BookmarkAdapter.BookmarkViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val job = getItem(position)
        holder.bind(job)
    }

    class BookmarkViewHolder(private val binding: ItemJobBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job: BookmarkedJob) {
            binding.title.text = job.title
            binding.location.text = job.job_location_slug
            if(job.salary_max==null || job.salary_min==null)
                binding.salary.isVisible = false
            else binding.salary.text = "${job.salary_min} - ${job.salary_max}"

            if(job.whatsapp_no==null)
                binding.phone.isVisible = false
            else
                binding.phone.text = "${job.whatsapp_no}"

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<BookmarkedJob>() {
        override fun areItemsTheSame(oldItem: BookmarkedJob, newItem: BookmarkedJob): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookmarkedJob, newItem: BookmarkedJob): Boolean {
            return oldItem == newItem
        }
    }
}

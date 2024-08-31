package com.meenal.jobhunt.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meenal.jobhunt.databinding.ItemJobBinding
import com.meenal.jobhunt.model.Job

class JobAdapter(private val onJobClick: (Job) -> Unit,
                 private val onJobBookmarked: (Job) -> Unit) :
    ListAdapter<Job, JobAdapter.JobViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = ItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("TAG","onCreateViewHolder")
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = getItem(position)
        Log.d("TAG","onBindViewHolder")
        holder.bind(job, onJobClick,onJobBookmarked)
    }

    class JobViewHolder(private val binding: ItemJobBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Job, onJobClick: (Job) -> Unit,onJobBookmarked: (Job) -> Unit) {

            Log.d("TAG","JobViewHolder")

            binding.title.text = job.title
            binding.location.text = job.job_location_slug
            if(job.salary_max==null || job.salary_min==null)
                binding.salary.isVisible = false
            else binding.salary.text = "${job.salary_min} - ${job.salary_max}"

            if(job.whatsapp_no==null)
                binding.phone.isVisible = false
            else
                binding.phone.text = "${job.whatsapp_no}"

            binding.bookmark.setOnClickListener { onJobBookmarked(job) }
            binding.root.setOnClickListener { onJobClick(job) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            Log.d("TAG","areItemsTheSame ${oldItem.id} == ${newItem.id} ${oldItem.id == newItem.id}")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            Log.d("TAG","areContentsTheSame ${oldItem.id} == ${newItem.id} ${oldItem.id == newItem.id}")

            return oldItem == newItem
        }
    }
}

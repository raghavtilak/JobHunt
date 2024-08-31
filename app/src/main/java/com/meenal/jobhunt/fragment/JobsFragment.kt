package com.meenal.jobhunt.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meenal.jobhunt.R
import com.meenal.jobhunt.adapter.JobAdapter
import com.meenal.jobhunt.databinding.FragmentJobsBinding
import com.meenal.jobhunt.viewmodel.JobViewModel

class JobsFragment : Fragment() {
    private lateinit var jobViewModel: JobViewModel
    private lateinit var jobAdapter: JobAdapter

    var _binding: FragmentJobsBinding?=null
    val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobViewModel = ViewModelProvider(this).get(JobViewModel::class.java)
        jobAdapter = JobAdapter({ job ->
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            val bundle = Bundle().apply {
                putParcelable("job",job) //string and value.
            }

            val fragInfo = JobDetailsFragment() //otpFragment is Another Fragment name
            fragInfo.arguments = bundle //bundle in which values stored
            fragmentTransaction.add(R.id.fragment_container,fragInfo) //replace in main activity
            fragmentTransaction.commit()
        }) { job ->

            jobViewModel.bookmarkJob(job)
            // Handle job click event
        }


        jobViewModel.jobs.observe(viewLifecycleOwner) { jobs ->
            Log.d("TAG","LIST TO BE SUBMIT= $jobs")
            jobAdapter.submitList(jobs.toMutableList())
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = jobAdapter

        jobViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        jobViewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Log.d("TAG","Job fragment $error")
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }

        // Pagination: Load more data when reaching the end of the list
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPosition >= totalItemCount - 1) {
                    jobViewModel.loadMoreJobs() // Load more jobs when the user reaches the end
                }
            }
        })

        jobViewModel.fetchJobs()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        _binding=null
        super.onDestroy()
    }

}


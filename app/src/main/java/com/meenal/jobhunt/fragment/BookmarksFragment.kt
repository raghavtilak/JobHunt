package com.meenal.jobhunt.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.meenal.jobhunt.adapter.BookmarkAdapter
import com.meenal.jobhunt.databinding.FragmentBookmarksBinding
import com.meenal.jobhunt.databinding.FragmentJobsBinding
import com.meenal.jobhunt.viewmodel.JobViewModel

class BookmarksFragment : Fragment() {
    private lateinit var jobViewModel: JobViewModel
    private lateinit var bookmarkAdapter: BookmarkAdapter

    var _binding: FragmentBookmarksBinding?=null
    val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobViewModel = ViewModelProvider(this).get(JobViewModel::class.java)
        bookmarkAdapter = BookmarkAdapter()

        binding.recyclerView.adapter = bookmarkAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        jobViewModel.bookmarkedJobs.observe(viewLifecycleOwner) { jobs ->
            bookmarkAdapter.submitList(jobs)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        _binding=null
        super.onDestroy()
    }
}


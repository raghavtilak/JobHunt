package com.meenal.jobhunt.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.meenal.jobhunt.databinding.FragmentJobDetailsBinding
import com.meenal.jobhunt.model.Job

class JobDetailsFragment : Fragment() {

    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val job = arguments?.getParcelable<Job>("job")

        job?.let { job->
            binding.title.text = job.title
            binding.location.text = job.job_location_slug
            binding.salary.text = "${job.salary_min} - ${job.salary_max}"
            binding.phone.text = "${job.whatsapp_no}"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

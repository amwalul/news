package com.example.news.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.data.Loading
import com.example.news.data.onFailure
import com.example.news.data.onSuccess
import com.example.news.databinding.FragmentListBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel by viewModels<ListViewModel>()

    private var _binding: FragmentListBinding? = null

    private val binding get() = _binding!!

    private val adapter = NewsAdapter { data ->
        val directions = ListFragmentDirections.actionListFragmentToDetailFragment(data)
        findNavController().navigate(directions)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getNews("id", getString(R.string.business))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initViews() = binding.apply {
        cgCategory.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds.first())
            viewModel.getNews("id", chip.text.toString())
        }

        rvNews.adapter = adapter
    }

    private fun initObservers() = with(viewModel) {
        newsResourceLiveData.observe(viewLifecycleOwner) { resource ->
            binding.apply {
                rvNews.isVisible = resource !is Loading
                progressLoading.isVisible = resource is Loading
            }

            resource
                .onSuccess { adapter.submitList(it.articles) }
                .onFailure { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
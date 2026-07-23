package com.divineiq.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.divineiq.app.DivineIQApplication
import com.divineiq.app.adapter.HomeAdapter
import com.divineiq.app.model.HomeItem
import com.divineiq.app.databinding.FragmentHomeBinding
import com.divineiq.app.utils.UiState
import com.divineiq.app.viewmodel.HomeViewModel
import com.divineiq.app.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

/**
 * Home tab: shows a greeting and a swipe-to-refresh list of recommended
 * learning content pulled from the remote API.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory((requireActivity().application as DivineIQApplication).appContainer)
    }

    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeAdapter(onItemClick = ::onHomeItemClicked)
        binding.recyclerHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }

        binding.swipeRefreshHome.setOnRefreshListener {
            viewModel.loadHomeItems()
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state -> render(state) }
    }

    private fun render(state: UiState<List<HomeItem>>) {
        binding.swipeRefreshHome.isRefreshing = state is UiState.Loading
        when (state) {
            is UiState.Loading -> Unit
            is UiState.Success -> {
                adapter.submitList(state.data)
                binding.layoutHomeEmpty.visibility =
                    if (state.data.isEmpty()) View.VISIBLE else View.GONE
                binding.recyclerHome.visibility =
                    if (state.data.isEmpty()) View.GONE else View.VISIBLE
            }
            is UiState.Error -> {
                Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun onHomeItemClicked(item: HomeItem) {
        Snackbar.make(binding.root, item.title, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerHome.adapter = null
        _binding = null
    }
}

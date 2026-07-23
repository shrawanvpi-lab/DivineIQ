package com.divineiq.app.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.divineiq.app.DivineIQApplication
import com.divineiq.app.adapter.QuizAdapter
import com.divineiq.app.model.Quiz
import com.divineiq.app.databinding.FragmentQuizBinding
import com.divineiq.app.utils.UiState
import com.divineiq.app.viewmodel.QuizViewModel
import com.divineiq.app.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

/**
 * Quiz tab: shows a swipe-to-refresh list of quizzes pulled from the
 * remote API.
 */
class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuizViewModel by viewModels {
        ViewModelFactory((requireActivity().application as DivineIQApplication).appContainer)
    }

    private lateinit var adapter: QuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = QuizAdapter(onQuizClick = ::onQuizClicked)
        binding.recyclerQuiz.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@QuizFragment.adapter
        }

        binding.swipeRefreshQuiz.setOnRefreshListener {
            viewModel.loadQuizzes()
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state -> render(state) }
    }

    private fun render(state: UiState<List<Quiz>>) {
        binding.swipeRefreshQuiz.isRefreshing = state is UiState.Loading
        when (state) {
            is UiState.Loading -> Unit
            is UiState.Success -> {
                adapter.submitList(state.data)
                binding.layoutQuizEmpty.visibility =
                    if (state.data.isEmpty()) View.VISIBLE else View.GONE
                binding.recyclerQuiz.visibility =
                    if (state.data.isEmpty()) View.GONE else View.VISIBLE
            }
            is UiState.Error -> {
                Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun onQuizClicked(quiz: Quiz) {
        Snackbar.make(binding.root, quiz.title, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerQuiz.adapter = null
        _binding = null
    }
}

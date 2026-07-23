package com.divineiq.app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.divineiq.app.DivineIQApplication
import com.divineiq.app.R
import com.divineiq.app.model.UserProfile
import com.divineiq.app.databinding.FragmentProfileBinding
import com.divineiq.app.viewmodel.ProfileViewModel
import com.divineiq.app.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

/**
 * Profile tab: shows the current user's info, usage stats, and account
 * actions (settings, about, log out).
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory((requireActivity().application as DivineIQApplication).appContainer)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profile.collect { profile -> render(profile) }
            }
        }

        binding.rowSettings.setOnClickListener {
            Snackbar.make(binding.root, R.string.profile_settings, Snackbar.LENGTH_SHORT).show()
        }
        binding.rowAbout.setOnClickListener {
            Snackbar.make(binding.root, R.string.profile_about, Snackbar.LENGTH_SHORT).show()
        }
        binding.rowLogout.setOnClickListener {
            Snackbar.make(binding.root, R.string.profile_logout, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun render(profile: UserProfile) {
        binding.textProfileName.text = profile.name
        binding.textProfileEmail.text = profile.email
        binding.textStatNotes.text = profile.notesCount.toString()
        binding.textStatQuizzes.text = profile.quizzesCount.toString()
        binding.textStatStreak.text = profile.streakDays.toString()
        binding.imageProfileAvatar.load(profile.avatarUrl) {
            crossfade(true)
            placeholder(R.drawable.bg_circle_surface_variant)
            error(R.drawable.bg_circle_surface_variant)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

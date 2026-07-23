package com.divineiq.app.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.divineiq.app.DivineIQApplication
import com.divineiq.app.R
import com.divineiq.app.adapter.NotesAdapter
import com.divineiq.app.model.Note
import com.divineiq.app.databinding.DialogAddNoteBinding
import com.divineiq.app.databinding.FragmentNotesBinding
import com.divineiq.app.viewmodel.NotesViewModel
import com.divineiq.app.viewmodel.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

/**
 * Notes tab: lets the user browse, add, and delete personal notes,
 * persisted offline in Room.
 */
class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels {
        ViewModelFactory((requireActivity().application as DivineIQApplication).appContainer)
    }

    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NotesAdapter(onDeleteClick = ::onDeleteNote)
        binding.recyclerNotes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@NotesFragment.adapter
        }

        binding.fabAddNote.setOnClickListener { showAddNoteDialog() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notes.collect { notes -> render(notes) }
            }
        }
    }

    private fun render(notes: List<Note>) {
        adapter.submitList(notes)
        binding.layoutNotesEmpty.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
        binding.recyclerNotes.visibility = if (notes.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun onDeleteNote(note: Note) {
        viewModel.deleteNote(note)
        Snackbar.make(binding.root, "Note deleted", Snackbar.LENGTH_SHORT).show()
    }

    private fun showAddNoteDialog() {
        val dialogBinding = DialogAddNoteBinding.inflate(layoutInflater)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.add_note)
            .setView(dialogBinding.root)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val title = dialogBinding.editNoteTitle.text?.toString().orEmpty().trim()
                val content = dialogBinding.editNoteContent.text?.toString().orEmpty().trim()
                viewModel.addNote(title, content)
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerNotes.adapter = null
        _binding = null
    }
}

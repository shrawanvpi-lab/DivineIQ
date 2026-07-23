package com.divineiq.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.divineiq.app.R
import com.divineiq.app.model.Quiz
import com.divineiq.app.databinding.ItemQuizBinding

/**
 * Displays available [Quiz]zes in a vertically scrolling list of cover
 * cards.
 */
class QuizAdapter(
    private val onQuizClick: (Quiz) -> Unit
) : ListAdapter<Quiz, QuizAdapter.QuizViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = ItemQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizViewHolder(binding, onQuizClick)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class QuizViewHolder(
        private val binding: ItemQuizBinding,
        private val onQuizClick: (Quiz) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(quiz: Quiz) {
            binding.textQuizTitle.text = quiz.title
            binding.textQuizDescription.text = quiz.description
            binding.chipQuizQuestionCount.text = binding.root.context.getString(
                R.string.quiz_question_count, quiz.questionCount
            )
            binding.imageQuizCover.load(quiz.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.bg_rounded_image_placeholder)
                error(R.drawable.bg_rounded_image_placeholder)
            }
            binding.root.setOnClickListener { onQuizClick(quiz) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Quiz>() {
            override fun areItemsTheSame(oldItem: Quiz, newItem: Quiz) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Quiz, newItem: Quiz) = oldItem == newItem
        }
    }
}

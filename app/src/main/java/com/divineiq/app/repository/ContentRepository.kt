package com.divineiq.app.repository

import com.divineiq.app.model.HomeItem
import com.divineiq.app.model.Quiz
import com.divineiq.app.model.UserProfile
import com.divineiq.app.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val CATEGORIES = listOf("Astronomy", "History", "Physics", "Biology", "Philosophy", "Mathematics")

/**
 * Source of truth for remote learning content (Home recommendations and
 * Quizzes). Notes and Bookmarks live in Room instead — see
 * [NotesRepository] and [BookmarkRepository] — since they're local,
 * user-authored data rather than server content.
 *
 * The last successful fetch of each list is cached in memory so
 * [SearchRepository] can filter across content without re-hitting the
 * network on every keystroke.
 */
class ContentRepository {

    private val api = RetrofitClient.contentApiService

    @Volatile private var cachedHomeItems: List<HomeItem> = emptyList()
    @Volatile private var cachedQuizzes: List<Quiz> = emptyList()

    val lastHomeItems: List<HomeItem> get() = cachedHomeItems
    val lastQuizzes: List<Quiz> get() = cachedQuizzes

    suspend fun getHomeItems(): Result<List<HomeItem>> = withContext(Dispatchers.IO) {
        runCatching {
            api.getPhotos(limit = 20).mapIndexed { index, dto ->
                HomeItem(
                    id = dto.id,
                    title = dto.title.replaceFirstChar { it.uppercase() },
                    subtitle = "Explore this topic in depth",
                    category = CATEGORIES[index % CATEGORIES.size],
                    imageUrl = dto.url
                )
            }
        }.onSuccess { cachedHomeItems = it }
    }

    suspend fun getQuizzes(): Result<List<Quiz>> = withContext(Dispatchers.IO) {
        runCatching {
            api.getPosts(limit = 15).mapIndexed { index, dto ->
                Quiz(
                    id = dto.id,
                    title = dto.title.replaceFirstChar { it.uppercase() },
                    description = dto.body.replace("\n", " ").take(90).trim() + "…",
                    questionCount = 5 + (index % 4) * 5,
                    imageUrl = "https://picsum.photos/seed/${dto.id}/400/300"
                )
            }
        }.onSuccess { cachedQuizzes = it }
    }

    /**
     * Base profile info for the current session. [UserProfile.notesCount]
     * is a placeholder here — [com.divineiq.app.viewmodel.ProfileViewModel]
     * overlays the live count from [NotesRepository] before this reaches
     * the UI, since that data lives in Room, not this class.
     */
    fun getUserProfile(): UserProfile = UserProfile(
        name = "Guest User",
        email = "guest@divineiq.app",
        avatarUrl = "https://i.pravatar.cc/150?img=12",
        notesCount = 0,
        quizzesCount = cachedQuizzes.size,
        streakDays = 4
    )
}

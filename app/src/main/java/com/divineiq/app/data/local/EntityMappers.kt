package com.divineiq.app.data.local

import com.divineiq.app.data.local.entity.BookmarkEntity
import com.divineiq.app.data.local.entity.NoteEntity
import com.divineiq.app.model.Bookmark
import com.divineiq.app.model.BookmarkType
import com.divineiq.app.model.Note

fun NoteEntity.toDomain(): Note = Note(
    id = id,
    title = title,
    content = content,
    createdAtMillis = createdAtMillis
)

fun Bookmark.toEntity(): BookmarkEntity = BookmarkEntity(
    id = id,
    refId = refId,
    type = type.name,
    title = title,
    createdAtMillis = createdAtMillis
)

fun BookmarkEntity.toDomain(): Bookmark = Bookmark(
    id = id,
    refId = refId,
    type = runCatching { BookmarkType.valueOf(type) }.getOrDefault(BookmarkType.HOME_ITEM),
    title = title,
    createdAtMillis = createdAtMillis
)

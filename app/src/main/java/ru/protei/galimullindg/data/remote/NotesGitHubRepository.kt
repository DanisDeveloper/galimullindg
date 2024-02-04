package ru.protei.galimullindg.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.protei.galimullindg.domain.Note
import ru.protei.galimullindg.domain.NotesRemoteRepository
import javax.inject.Inject

class NotesGitHubRepository @Inject constructor(private val notesApi: NotesGitHubApi) : NotesRemoteRepository {
    override suspend fun list(): List<Note> = withContext(Dispatchers.IO) {
        val issues: List<GitHubIssue>?
        try {
            val result = notesApi.getList()
            if (!result.isSuccessful) {
                Log.w("NotesGitHubRepository", "Can't get issues $result")
                return@withContext emptyList()
            }
            issues = result.body()
        } catch (e: Exception) {
            Log.w("NotesGitHubRepository", "Can't get issues ", e)
            return@withContext emptyList()
        }
        issues?.forEachIndexed { index, item ->
            Log.i("issues", "$index  -  $item")
        }
        return@withContext issues?.map<GitHubIssue, Note> { toNote(it) } ?: emptyList<Note>()
    }

    override suspend fun add(note: Note): Long? = withContext(Dispatchers.IO) {
        val issue: GitHubIssue?
        try {
            val result = notesApi.add(toGitHubIssue(note))
            if (!result.isSuccessful) {
                Log.w("NotesGitHubRepository", "Can't get issues $result")
                return@withContext null
            }
            issue = result.body()
        } catch (e: Exception) {
            Log.w("NotesGitHubRepository", "Can't add issue", e)
            return@withContext null
        }
        return@withContext issue?.number
    }

    override suspend fun update(note: Note): Boolean = withContext(Dispatchers.IO) {
        try {
            val result = notesApi.update(
                note.remoteId!!,
                GitHubIssue(null, note.title, note.text, null)
            )
            if (!result.isSuccessful) {
                Log.w("NotesGitHubRepository", "Can't add issue")
                return@withContext false
            }
        } catch (e: Exception) {
            Log.w("NotesGitHubRepository", "Can't add issue", e)
            return@withContext false
        }
        return@withContext true
    }

    override suspend fun delete(note: Note): Boolean {
        TODO("Not yet implemented")
    }

    private fun toNote(issue: GitHubIssue): Note {
        return Note(
            null,
            issue.title,
            issue.body ?: "",
            issue.number
        )
    }

    private fun toGitHubIssue(note: Note): GitHubIssue {
        return GitHubIssue(
            note.remoteId,
            note.title,
            note.text,
            null
        )
    }
}
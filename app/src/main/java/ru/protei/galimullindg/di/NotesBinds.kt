package ru.protei.galimullindg.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.protei.galimullindg.data.local.NotesRepositoryDB
import ru.protei.galimullindg.data.remote.NotesGitHubRepository
import ru.protei.galimullindg.domain.NotesRemoteRepository
import ru.protei.galimullindg.domain.NotesRepository


@Module
@InstallIn(SingletonComponent::class)
abstract class NotesBinds {

    @Binds
    abstract fun bindNotesRepository(notesRepositoryDB: NotesRepositoryDB): NotesRepository

    @Binds
    abstract fun bindNotesRemoteRepository(notesGitHubRepository: NotesGitHubRepository): NotesRemoteRepository
}
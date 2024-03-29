package ru.protei.galimullindg.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var title: String,
    var text: String,
    var remoteId: Long? = null
)
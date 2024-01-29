package ru.protei.galimullindg.data.remote

data class GitHubIssue(
    val number: Long?,
    val title: String,
    val body: String?,
    var state: String?
)
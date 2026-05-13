package ec.edu.uisek.githubclient.models

data class RepositoryPayload(
    val name: String,
    val description: String? = null,
)

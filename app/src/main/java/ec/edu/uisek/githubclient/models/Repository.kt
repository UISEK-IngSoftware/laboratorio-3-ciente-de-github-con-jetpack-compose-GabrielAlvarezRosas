package ec.edu.uisek.githubclient.models

data class Repository(
    val id: String,
    val name: String,
    val owner: GithubUser,
    val description: String?,
    val language: String?
)

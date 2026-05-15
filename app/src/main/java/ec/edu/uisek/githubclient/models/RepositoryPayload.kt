package ec.edu.uisek.githubclient.models

data class RepositoryPayload(
    val name: String? = null, // se agrega null para que al actualizar, se pueda o no actualizar un campo solamente
    val description: String? = null,
)

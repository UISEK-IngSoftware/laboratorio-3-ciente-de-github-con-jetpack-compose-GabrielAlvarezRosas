package ec.edu.uisek.githubclient.services

import androidx.compose.ui.text.style.TextDirection
import ec.edu.uisek.githubclient.models.Repository
import ec.edu.uisek.githubclient.models.RepositoryPayload
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET(value="user/repos")
    suspend fun getRepository(
        @Query(value = "sort") sort: String ="created",
        @Query("direction") direction: String = "desc",
        @Query(value = "affiliation") affiliation: String = "owner",
        @Query ("t") t: String= "${System.currentTimeMillis()}"

    ): List<Repository>

    @POST(value="user/repos")
    suspend fun createRepository(
        @Body payload: RepositoryPayload
    ): Repository
}
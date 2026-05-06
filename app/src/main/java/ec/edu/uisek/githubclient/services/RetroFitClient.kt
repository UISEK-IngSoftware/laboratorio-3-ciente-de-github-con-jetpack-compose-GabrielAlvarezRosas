package ec.edu.uisek.githubclient.services


import androidx.compose.ui.geometry.isEmpty
import ec.edu.uisek.githubclient.services.ApiService
import ec.edu.uisek.githubclient.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "https://api.github.com/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
            val token = BuildConfig.GITHUB_TOKEN
            println("DEBUG_TOKEN: Es vacío? ${token.isEmpty()}") // No imprimas el token completo por seguridad

            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token") // Asegúrate de que hay un espacio después de Bearer
                .build()
            chain.proceed(request)
        }
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
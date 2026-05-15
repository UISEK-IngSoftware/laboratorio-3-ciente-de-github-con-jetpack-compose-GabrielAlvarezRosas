package ec.edu.uisek.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.githubclient.models.Repository
import ec.edu.uisek.githubclient.ui.screens.RepoForm
import ec.edu.uisek.githubclient.ui.screens.RepoList
import ec.edu.uisek.githubclient.ui.theme.GithubClientTheme
import ec.edu.uisek.githubclient.viewmodels.RepoFormViewModel
import ec.edu.uisek.githubclient.viewmodels.RepoListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GithubClientTheme {
                val listViewModel: RepoListViewModel = viewModel()
                //añadimos el formviewmodel para el delete
                val formViewModel: RepoFormViewModel = viewModel()
                var currentScreen by remember { mutableStateOf("RepoList") }
                var selectedRepo by remember { mutableStateOf<Repository?>(null) }
                when (currentScreen) {
                    "RepoList" -> RepoList(
                        viewModel = listViewModel,
                        onNavigateToForm = {
                            selectedRepo = null // IMPORTANTE: Limpiar para nuevo repo
                            currentScreen = "RepoForm"
                        },
                    //agregamos los parámetros para editar y eliminar
                        onNavigateToEdit = {repo ->
                            selectedRepo = repo
                            currentScreen = "RepoForm"
                        },
                        onNavigateToDelete = {repo ->
                            formViewModel.deleteRepository(repo.owner.login, repo.name)
                        }
                    )

                    "RepoForm" -> RepoForm(
                        owner= selectedRepo?.owner?.login?:"",
                        initialName = selectedRepo?.name ?: "",
                        initialDescription = selectedRepo?.description ?: "",
                        isEditing = selectedRepo != null,
                        onSaveSuccess = {
                            listViewModel.fetchRepos()
                            selectedRepo = null
                            currentScreen = "RepoList" },
                        onBackClick = {
                            selectedRepo = null
                            currentScreen = "RepoList" }
                    )
                }
            }
        }
    }
}
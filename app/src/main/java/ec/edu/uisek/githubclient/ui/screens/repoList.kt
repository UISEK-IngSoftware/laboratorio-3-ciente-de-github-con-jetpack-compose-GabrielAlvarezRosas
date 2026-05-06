package ec.edu.uisek.githubclient.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.githubclient.ui.components.RepoItem
import ec.edu.uisek.githubclient.ui.theme.GithubClientTheme
import ec.edu.uisek.githubclient.viewmodels.RepoListViewModel


@Composable
fun RepoList(
    modifier: Modifier= Modifier,
    viewModel: RepoListViewModel = viewModel()
){
    val repos by viewModel.repos.collectAsState()
    val isLoading by viewModel.isLoanding.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        if (isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align (Alignment.Center)
            )
        }


    errorMsg?.let { message ->
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }

    if (!isLoading && errorMsg==null){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(repos.size){i ->
                RepoItem(repos[i])
            }
        }
    }
    }
}


package ec.edu.uisek.githubclient.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.githubclient.models.Repository
import ec.edu.uisek.githubclient.ui.components.RepoItem
import ec.edu.uisek.githubclient.viewmodels.RepoListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoList(
    modifier: Modifier = Modifier,
    viewModel: RepoListViewModel = viewModel(),
    onNavigateToForm: () -> Unit = {},
    // añadimos nuevos parámetros para editar y eliminar
    onNavigateToEdit: (Repository) -> Unit = {},
    onNavigateToDelete: (Repository) -> Unit = {}

){
    val repos by viewModel.repos.collectAsState()
    val isLoading by viewModel.isLoanding.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToForm,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir repositorio"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
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

            if (!isLoading && errorMsg == null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Usamos el ID del repo como clave para que las animaciones de swipe funcionen correctamente
                    items(
                        count = repos.size,
                        key = { index -> repos[index].id }
                    ) { i ->
                        val repo = repos[i]
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { dismissValue ->
                                if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                    onNavigateToDelete(repo)
                                    true // Confirmamos el barrido visual
                                } else false
                            }
                        )
                        LaunchedEffect(repo.id) {
                            if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
                                dismissState.reset()
                            }
                        }

                        SwipeToDismissBox(
                            state = dismissState,
                            enableDismissFromEndToStart =  true,
                            enableDismissFromStartToEnd = false,
                            backgroundContent = {
                                val color by animateColorAsState(
                                    when (dismissState.targetValue) {
                                        SwipeToDismissBoxValue.EndToStart-> MaterialTheme.colorScheme.error
                                        else -> Color.Transparent
                                    }, label = "background"
                                )
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ){
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Eliminar repositorio",
                                        tint = Color.White
                                    )
                                }
                            }
                        ) {
                            //creamos una lista que envuelva a repoitem para agregar eliminar
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surface),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Box(modifier= Modifier.weight(1f)){
                                    RepoItem(repo)
                                }

                                    IconButton(onClick = {onNavigateToEdit(repo)}) {
                                     Icon(
                                         imageVector = Icons.Default.Edit,
                                         contentDescription= "Editar repositorio",
                                         tint = MaterialTheme.colorScheme.primary
                                     )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun RepoListPreview (){
    RepoList()
}
package ec.edu.uisek.githubclient.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoList(
    modifier: Modifier = Modifier,
    viewModel: RepoListViewModel = viewModel(),
    onNavigateToForm: () -> Unit = {},
    // añadimos nuevos parámetros para editar y eliminar
    onNavigateToEdit: (Repository) -> Unit = {},
    onNavigateToDelete: (Repository) -> Unit = {},
    onLogout:() -> Unit = {}


){
    val repos by viewModel.repos.collectAsState()
    val isLoading by viewModel.isLoanding.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

    //creamos una variable para el diálogo de eliminar
    var showDeleteDialog by remember { mutableStateOf(false) }
    var repoToDelete by remember { mutableStateOf<Repository?>(null) }

    //variable para controlar advertencia del logout
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                FloatingActionButton(
                    onClick = { showLogoutDialog = true },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Cerrar sesión"
                    )
                }
                if (showLogoutDialog) {
                    AlertDialog(
                        onDismissRequest = { showLogoutDialog = false },
                        title = { Text("¿Cerrar sesión?") },
                        text = { Text("Tendrás que ingresar tu token de nuevo.") },
                        confirmButton = {
                            TextButton(onClick = {
                                showLogoutDialog = false
                                onLogout() // Aquí sí ejecutas la acción real
                            }) { Text("Salir") }
                        },
                        dismissButton = {
                            TextButton(onClick = { showLogoutDialog = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                FloatingActionButton(
                    onClick = onNavigateToForm,
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir repositorio"
                    )
                }
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
                    // Usamos el id del repo como clave para que las animaciones de swipe funcionen correctamente
                    items(
                        count = repos.size,
                        key = { index -> repos[index].id }
                    ) { i ->
                        val repo = repos[i]
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { dismissValue ->
                                //Al ejecutar el swipe se actualizan las variables y se inicia el dialogo
                                if (dismissValue == SwipeToDismissBoxValue.EndToStart) {

                                    repoToDelete = repo
                                    showDeleteDialog = true

                                    false
                                } else {
                                    false
                                }
                            }
                        )
                        //Se resetea el dismiss state una vez cerrado el swipe
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
                                         //usamos el icono por defecto para editar
                                         imageVector = Icons.Default.Edit,
                                         contentDescription= "Editar repositorio",
                                         tint = MaterialTheme.colorScheme.primary
                                     )
                                }
                            }
                        }
                    }
                }
                //el cuadro de diálogo se activa solo tras un swipe que da valor a ambos parámtros
                if (showDeleteDialog && repoToDelete != null) {

                    AlertDialog(

                        //para cuando el usuario topa fuera del mensaje
                        onDismissRequest = {
                            showDeleteDialog = false
                            repoToDelete = null
                        },
                        //contenido del mensaje
                        title = {
                            Text("Eliminar repositorio")
                        },

                        text = {
                            Text("¿Estás seguro de eliminar este repositorio?")
                        },
                        //botones del mensaje
                        confirmButton = {

                            TextButton(
                                onClick = {

                                    repoToDelete?.let {
                                        onNavigateToDelete(it)
                                    }

                                    showDeleteDialog = false
                                    repoToDelete = null
                                }
                            ) {
                                Text("Eliminar")
                            }
                        },

                        dismissButton = {

                            TextButton(
                                onClick = {

                                    showDeleteDialog = false
                                    repoToDelete = null
                                }
                            ) {
                                Text("Cancelar")
                            }
                        }
                    )
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
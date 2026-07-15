package ec.edu.uisek.githubclient.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.githubclient.viewmodels.RepoFormViewModel

// Preview corregido
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RepoFormPreview() {
    // Reemplaza por el nombre real de tu tema (ej. GithubClientTheme)
    MaterialTheme {
        RepoForm(
            onSaveSuccess = { },
            onBackClick = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoForm(
    //agregamos nuevos parámetros que se usaran según la acción a realizar
    owner: String = "",
    initialName: String = "",
    initialDescription: String = "",
    isEditing: Boolean = false,
    onSaveSuccess: () -> Unit ={},
    onBackClick: () -> Unit = {},
    viewModel: RepoFormViewModel = viewModel()
) {
    val isLoanding by viewModel.isLoanding.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

    //modificamos los parámetros para que en caso de editar se muestren datos previos
    var name by remember { mutableStateOf(initialName) }
    var description by remember { mutableStateOf(initialDescription) }

    LaunchedEffect(key1= isSuccess){
        if(isSuccess){
            onSaveSuccess()
            viewModel.resetSuccess()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                //Se agrega un titulo según la acción a realizar
                title = { Text(if (isEditing) "Editar Repositorio" else "Nuevo Repositorio") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        // Contenedor principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center // Centro el contenido del Box
        ) {
            Column(
                // Ajustamos la columna para que no ocupe todo el alto,
                // sino solo lo necesario y esté centrada
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                LaunchedEffect(initialName, initialDescription) {
                    name = initialName
                    description = initialDescription
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre del repositorio") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = true
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Button(
                    onClick = {
                        if (isEditing){
                            //Si estamos editando, llamamos a la función de actualización
                            viewModel.updateRepository(owner, initialName, name, description)
                        }else{
                            viewModel.createRepository(name, description)
                        }
                   },
                    enabled = name.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isEditing)"Actualizar Cambios" else "Guardar Repositorio")
                }
            }
        }
    }
}

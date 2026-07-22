package ec.edu.uisek.githubclient.ui.screens

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import ec.edu.uisek.githubclient.ui.theme.GithubClientTheme
import kotlin.math.sin
import androidx.compose.ui.platform.LocalContext
import ec.edu.uisek.githubclient.services.AuthService

@Composable
fun Loginform(
    onLoginSuccess: () -> Unit = {}
){
    val context = LocalContext.current
    val authService = remember { AuthService(context) }
    var username by remember { mutableStateOf(value = "") }
    var token by remember { mutableStateOf(value = "") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ingresa al cliente Github",
            style= MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = username,
            onValueChange = {username = it},
            label = {Text(text="Usuario")},
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(height = 16.dp))
        OutlinedTextField(
            value = token,
            onValueChange = {token = it},
            label = {Text(text="Token de github")},
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(height = 16.dp))

        Button(
            onClick = {authService.saveAuth(username, token)
                        onLoginSuccess()},
            modifier = Modifier.fillMaxWidth(),
            enabled = username.isNotBlank() && token.isNotBlank()
        ) {
            Text(text = "Ingresar")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    GithubClientTheme {
        Loginform()
    }
}
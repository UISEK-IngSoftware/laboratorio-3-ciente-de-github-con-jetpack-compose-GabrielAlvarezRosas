package ec.edu.uisek.githubclient.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ec.edu.uisek.githubclient.ui.components.RepoItem
import ec.edu.uisek.githubclient.ui.theme.GithubClientTheme


@Composable
fun RepoList(){
    Column(
        modifier = Modifier
            .padding(top =40.dp, start = 16.dp, end= 16.dp, bottom = 16.dp)
    ) {
        RepoItem(
            repoName= "Repositorio de Android",
            repoDescription= "Este es un repositorio construido en JetpackCompose",
            repoLanguage="Kotlin",
            repoImage="https://avatars.githubusercontent.com/u/205797987?v=4"
        )
        RepoItem(
            repoName= "Repositorio de Swift",
            repoDescription= "Este es un repositorio construido en Swift",
            repoLanguage="Swift",
            repoImage="https://avatars.githubusercontent.com/u/205797987?v=4"
        )
        RepoItem(
            repoName= "Repositorio de Django",
            repoDescription= "Este es un repositorio construido en Django",
            repoLanguage="Python",
            repoImage="https://avatars.githubusercontent.com/u/205797987?v=4"
        )
        RepoItem(
            repoName= "Repositorio de React",
            repoDescription= "Este es un repositorio construido en React",
            repoLanguage="Python",
            repoImage="https://avatars.githubusercontent.com/u/205797987?v=4"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RepoItemPreview () {
    GithubClientTheme {
        RepoList()
    }
}
package ec.edu.uisek.githubclient.ui.components

import android.accessibilityservice.GestureDescription
import android.media.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ec.edu.uisek.githubclient.models.GithubUser
import ec.edu.uisek.githubclient.models.Repository
import ec.edu.uisek.githubclient.ui.theme.GithubClientTheme
import org.intellij.lang.annotations.Language


@Composable
fun RepoItem(
    repository: Repository
){
    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = repository.owner.avatarUrl,
                contentDescription = repository.name,
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text= repository.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                if(!repository.description.isNullOrBlank()){
                    Text(
                        text= repository.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3
                    )
                }
                if(!repository.language.isNullOrBlank()){
                    Text(
                        text= repository.language,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 3
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RepoItemPreview(){
    GithubClientTheme{
        val repository= Repository(
            id="1234",
            name = "Lab 3",
            owner= GithubUser (
                id="0001",
                login = "gabrielalvarezosas",
                avatarUrl = "https://avatars.githubusercontent.com/u/205797987?v=4"
            ),
            description = "Jetpack Comopose",
            language = "kotlin"
        )
        RepoItem( repository  )
    }
}

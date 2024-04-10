package org.mathieu.cleanrmapi.ui.screens.characterdetails

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import org.mathieu.cleanrmapi.ui.core.composables.PreviewContent
import org.mathieu.cleanrmapi.ui.core.theme.Purple40
import org.mathieu.projet2.R

private typealias UIState = CharacterDetailsState

@Composable
fun CharacterDetailsScreen(
    navController: NavController,
    id: Int
) {
    val viewModel: CharacterDetailsViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    viewModel.init(characterId = id)

    CharacterDetailsContent(
        state = state,
        onClickBack = navController::popBackStack
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun CharacterDetailsContent(
    state: UIState = UIState(),
    onClickBack: () -> Unit = { },
    navController: NavController // Ajout d'un NavController pour gérer la navigation
) = Scaffold(topBar = {

    Row(
        modifier = Modifier
            .background(Purple40)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .clickable(onClick = onClickBack),
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White)
        )

        Text(
            text = state.name,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}) { paddingValues ->
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues), contentAlignment = Alignment.Center) {
        AnimatedContent(targetState = state.error != null, label = "") {
            state.error?.let { error ->
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = error,
                    textAlign = TextAlign.Center,
                    color = Purple40,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 36.sp
                )
            } ?: Box(modifier = Modifier.fillMaxSize()) {

                Box(Modifier.align(Alignment.TopCenter)) {

                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .blur(100.dp)
                            .alpha(0.3f)
                            .fillMaxWidth(),
                        model = state.avatarUrl,
                        contentDescription = null
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background
                                    )
                                )
                            )
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .shadow(3.dp),
                        model = state.avatarUrl,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = state.name)
                }
                // Ce bloc est exécuté lorsqu'une opération précédente (probablement une requête de données) réussit.
                onSuccess {
                    // Trie la liste des épisodes obtenus dans l'objet `it` par le code d'épisode.
                    // `it` fait référence à l'objet résultat de l'opération réussie, contenant probablement les détails d'un personnage
                    // et sa liste d'épisodes associés.
                    val sortedEpisodes = it.episode.sortedBy { episode -> episode.code }

                    // Met à jour l'état actuel en utilisant la fonction `updateState`. `updateState` est probablement une fonction définie
                    // pour manipuler l'état de l'UI ou d'un modèle de données dans une architecture MVVM ou MVI.
                    // La mise à jour de l'état se fait ici par la copie de l'état actuel tout en modifiant certaines de ses propriétés:
                    // - `avatarUrl` est mis à jour avec la nouvelle URL de l'avatar obtenue.
                    // - `name` est mis à jour avec le nouveau nom du personnage.
                    // - `episodes` est mis à jour avec la liste triée des épisodes.
                    // - `error` est réinitialisé à `null` pour indiquer qu'aucune erreur n'est actuellement présente.
                    updateState { copy(avatarUrl = it.avatarUrl, name = it.name, episodes = sortedEpisodes, error = null) }
                }



            }
        }
    }
}

// Décore une fonction composable nommée `EpisodeCard` qui prend en paramètre un objet `Episode`
// et une lambda `onClick` qui sera invoquée lorsque l'utilisateur clique sur la carte.
@Composable
fun EpisodeCard(episode: Episode, onClick: () -> Unit) {
    Row( // Crée une ligne horizontale qui va contenir les éléments de la carte.
        modifier = Modifier
            .fillMaxWidth() // Assure que la carte remplit la largeur maximale disponible de son conteneur parent.
            .clickable { onClick() } // Rend la carte cliquable et exécute la lambda `onClick` lorsqu'elle est cliquée.
            .padding(8.dp), // Ajoute un padding de 8dp autour de la carte pour la séparer des autres éléments.
        verticalAlignment = Alignment.CenterVertically // Centre verticalement les éléments à l'intérieur de la ligne.
    ) {
        Spacer(Modifier.width(8.dp)) // Insère un espace vide de 8dp pour un meilleur espacement visuel au début de la carte.

        Column {
            // Concaténation du code de l'épisode, d'un tiret et du titre de l'épisode
            Text(
                text = "${episode.code} - ${episode.name}",
                fontWeight = FontWeight.Bold
            )
            // Affichage de la date de l'épisode
            Text(
                text = "Air date: ${episode.airDate}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview
@Composable
private fun CharacterDetailsPreview() = PreviewContent {
    CharacterDetailsContent()
}


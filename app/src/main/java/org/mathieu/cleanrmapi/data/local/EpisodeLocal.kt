package org.mathieu.cleanrmapi.data.local

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mathieu.cleanrmapi.data.local.objects.CharacterObject
import org.mathieu.cleanrmapi.data.local.objects.EpisodeObject

// Définit une classe interne EpisodeLocal qui sert à interagir avec la base de données locale pour les opérations liées aux épisodes.
internal class EpisodeLocal(private val database: RealmDatabase) {

    // Récupère tous les épisodes stockés dans la base de données locale et les retourne sous forme de flux (Flow) de liste d'EpisodeObject.
    suspend fun getEpisodes(): Flow<List<EpisodeObject>> = database.use {
        query<EpisodeObject>().find().asFlow().map { it.list }
    }

    // Récupère un épisode spécifique par son identifiant depuis la base de données locale.
    // Retourne un EpisodeObject si trouvé, sinon null.
    suspend fun getEpisode(id: Int): EpisodeObject? = database.use {
        query<EpisodeObject>("id == $id").first().find()
    }

    // Prend une liste d'EpisodeObject et les sauvegarde dans la base de données.
    // Utilise la fonction insert pour chaque épisode de la liste.
    suspend fun saveEpisodes(episode: List<EpisodeObject>) = episode.onEach {
        insert(it)
    }

    // Insère ou met à jour un EpisodeObject dans la base de données.
    // Utilise la politique de mise à jour `UpdatePolicy.ALL` pour mettre à jour toutes les propriétés de l'objet existant ou en insérer un nouveau si inexistant.
    suspend fun insert(episode: EpisodeObject) {
        database.write {
            copyToRealm(episode, UpdatePolicy.ALL)
        }
    }

}

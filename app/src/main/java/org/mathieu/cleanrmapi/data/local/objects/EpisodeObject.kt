package org.mathieu.cleanrmapi.data.local.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mathieu.cleanrmapi.data.remote.responses.EpisodeResponse
import org.mathieu.cleanrmapi.domain.models.episode.Episode

/**
 * Represents an episode entity stored in the Realm database. This object provides fields
 * necessary to represent all the attributes of an episode from the data source.
 * The object is specifically tailored for Realm storage.
 *
 * @property id Unique identifier of the episode.
 * @property name Name of the episode.
 * @property air_date Air date of the episode.
 * @property episode Code of the episode, usually indicating its order within the series.
 * @property characters A list of character IDs appearing in this episode.
 * @property url URL pointing to the episode's detail page.
 * @property created Timestamp indicating when the episode entity was created in the database.
 */
internal class EpisodeObject: RealmObject {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var air_date: String = ""
    var episode: String = ""
    var url: String = ""
    var created: String = ""
}

/**
 * Converts an EpisodeResponse (API model) to an EpisodeObject (Realm database model).
 * This function maps fields from the API model to the database model.
 */
internal fun EpisodeResponse.toRealmObject() = EpisodeObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.air_date = air_date
    obj.episode = episode
    obj.url = url
    obj.created = created
}

/**
 * Converts an EpisodeObject (Realm database model) to an Episode (domain model).
 * This function maps fields from the database model to the domain model.
 */
internal fun EpisodeObject.toModel() = Episode(
    id = id,
    name = name,
    air_date = air_date,
    episode = episode,
    characters = emptyList(),
    url = url,
    created = created
)

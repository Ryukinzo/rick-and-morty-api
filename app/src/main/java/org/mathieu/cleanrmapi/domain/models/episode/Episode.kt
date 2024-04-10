package org.mathieu.cleanrmapi.domain.models.episode

import org.mathieu.cleanrmapi.domain.models.character.Character

/**
 * Represents detailed information about an episode, typically received from an API response.
 *
 * @property id The unique identifier for the episode.
 * @property name The name of the episode.
 * @property air_date The air date of the episode.
 * @property episode The code of the episode, usually indicating its order within the series.
 * @property characters A list of URLs referring to characters that appear in this episode.
 * @property url The unique URL endpoint specifically for this episode's data.
 * @property created The timestamp indicating when the episode was added to the database.
 */

data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<Character>,
    val url: String,
    val created: String
)
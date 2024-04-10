package org.mathieu.cleanrmapi.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.domain.models.character.Character
import org.mathieu.cleanrmapi.domain.models.episode.Episode

interface EpisodeRepository {
    /**
     * Fetches a list of characters from the data source. The function streams the results
     * as a [Flow] of [List] of [Character] objects.
     *
     * @return A flow emitting a list of characters.
     */
    suspend fun getEpisodes(): Flow<List<Episode>>

    /**
     * Fetches the details of a specific character based on the provided ID.
     *
     * @param id The unique identifier of the character to be fetched.
     * @return Details of the specified character.
     */
    suspend fun getEpisode(id: Int): Episode
}
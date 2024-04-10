package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import org.mathieu.cleanrmapi.data.remote.responses.CharacterResponse
import org.mathieu.cleanrmapi.data.remote.responses.EpisodeResponse
import org.mathieu.cleanrmapi.data.remote.responses.PaginatedResponse

internal class EpisodeAPI(private val client: HttpClient) {

    /**
     * Fetches a list of episodes from the API.
     *
     * If the page parameter is not provided, it defaults to fetching the first page.
     *
     * @param page The page number to fetch. If null, the first page is fetched by default.
     * @return A paginated response containing a list of [EpisodeResponse] for the specified page.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getEpisodes(page: Int?): PaginatedResponse<EpisodeResponse> = client
        .get("episode/") {
            if (page != null)
                url {
                    parameter("page", page)
                }
        }
        .accept(HttpStatusCode.OK)
        .body()


    /**
     * Fetches the details of an episode with the given ID from the service.
     *
     * @param id The unique identifier of the episode to retrieve.
     * @return The [CharacterResponse] representing the details of the episode.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getEpisode(id: Int): EpisodeResponse? = client
        .get("episode/$id")
        .accept(HttpStatusCode.OK)
        .body()

}
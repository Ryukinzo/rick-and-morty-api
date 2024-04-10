package org.mathieu.cleanrmapi.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import org.mathieu.cleanrmapi.data.local.CharacterLocal
import org.mathieu.cleanrmapi.data.local.EpisodeLocal
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.local.RealmDatabase
import org.mathieu.cleanrmapi.data.remote.CharacterApi
import org.mathieu.cleanrmapi.data.remote.EpisodeAPI
import org.mathieu.cleanrmapi.data.remote.createHttpClient
import org.mathieu.cleanrmapi.data.repositories.CharacterRepositoryImpl
import org.mathieu.cleanrmapi.data.repositories.EpisodeRepositoryImpl
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository
import org.mathieu.cleanrmapi.domain.repositories.EpisodeRepository

//https://rickandmortyapi.com/documentation/#rest
private const val RMAPI_URL = "https://rickandmortyapi.com/api/"

val dataModule = module {

    single<HttpClient> {
        createHttpClient(
            baseUrl = RMAPI_URL
        )
    }

    single<RealmDatabase> { RMDatabase() }

    single { CharacterLocal(get()) }

    single { CharacterApi(get()) }

    single<CharacterRepository> { CharacterRepositoryImpl(get(), get(), get()) }

    // Cette ligne définit un singleton de EpisodeLocal qui sera créé en appelant le constructeur de EpisodeLocal avec un paramètre obtenu par Koin.
    // get() essaie de résoudre la dépendance nécessaire pour le constructeur de EpisodeLocal.
    // EpisodeLocal est probablement une classe responsable de la gestion des données d'épisodes stockées localement, par exemple dans une base de données SQLite.
    single { EpisodeLocal(get()) }

    // Semblable à la ligne ci-dessus, cette ligne crée un singleton de EpisodeAPI.
    // Le constructeur de EpisodeAPI reçoit également une dépendance via get().
    // EpisodeAPI est vraisemblablement une classe destinée à gérer les appels réseau pour récupérer des données depuis une API externe.
    single { EpisodeAPI(get()) }

    // Cette ligne définit un singleton pour EpisodeRepository en utilisant l'implémentation EpisodeRepositoryImpl.
    // EpisodeRepository est une interface, et EpisodeRepositoryImpl est sa mise en œuvre concrète.
    // Le constructeur de EpisodeRepositoryImpl prend trois paramètres, tous résolus par Koin à l'aide de get().
    // Ces paramètres sont probablement des instances de EpisodeLocal, EpisodeAPI, et peut-être un autre service ou configuration nécessaire.
    // EpisodeRepository sert d'abstraction pour accéder aux données des épisodes, permettant de les récupérer soit localement soit via le réseau, en fonction de la logique métier.
    single<EpisodeRepository> { EpisodeRepositoryImpl(get(), get(), get()) }

}
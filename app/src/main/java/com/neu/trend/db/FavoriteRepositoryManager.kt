package com.neu.trend.db

import android.content.Context
import android.content.SharedPreferences

import com.neu.trend.domain.model.response.githubrepo.Repository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 *The task did not specify a particular database implementation, so I chose to use SharedPreferences.
 */
class FavoriteRepositoryManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val type = Types.newParameterizedType(List::class.java, Repository::class.java)
    private val adapter = moshi.adapter<List<Repository>>(type)

    fun addFavorite(repository: Repository) {
        val favorites = getFavorites().toMutableList()
        if (favorites.none { it.id == repository.id }) {
            favorites.add(repository)
            saveFavorites(favorites)
        }
    }

    fun removeFavorite(repository: Repository) {
        val favorites = getFavorites().toMutableList()
        val index = favorites.indexOfFirst { it.id == repository.id }
        if (index != -1) {
            favorites.removeAt(index)
            saveFavorites(favorites)
        }
    }

    fun getFavorites(): List<Repository> {
        val json = sharedPreferences.getString("favorite_repositories", null) ?: return emptyList()
        return adapter.fromJson(json) ?: emptyList()
    }

    private fun saveFavorites(favorites: List<Repository>) {
        val editor = sharedPreferences.edit()
        val json = adapter.toJson(favorites)
        editor.putString("favorite_repositories", json)
        editor.apply()
    }
}
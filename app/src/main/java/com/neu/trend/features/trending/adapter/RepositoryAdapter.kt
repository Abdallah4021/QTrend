package com.neu.trend.features.trending.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.neu.trend.R
import com.neu.trend.db.FavoriteRepositoryManager
import com.neu.trend.domain.model.response.githubrepo.Repository
import com.neu.trend.features.detials.RepositoryDetailActivity
import com.neu.trend.features.trending.model.FavoriteRepositoryEvent
import org.greenrobot.eventbus.EventBus

class RepositoryAdapter(private val context: Context) :
    ListAdapter<Repository, RepositoryAdapter.RepositoryViewHolder>(RepositoryDiffCallback()) {

    private val favoriteRepositoryIds = mutableSetOf<Int>()
    private val currentRepositories = mutableListOf<Repository>()
    private var filteredRepositories: List<Repository> = listOf()
    val favoriteRepositoryManager = FavoriteRepositoryManager(context = context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(filteredRepositories[position])
    }

    override fun getItemCount(): Int {
        return filteredRepositories.size
    }

    fun updateRepositories(repositories: List<Repository>) {
        if (currentRepositories.isEmpty()) {
            currentRepositories.addAll(repositories)
            submitList(currentRepositories)
        } else {
            val oldSize = currentRepositories.size
            currentRepositories.addAll(repositories)
            notifyItemRangeInserted(oldSize, repositories.size)
        }
        filteredRepositories = currentRepositories;
    }

    fun filter(query: String) {
        filteredRepositories = if (query.isEmpty()) {
            currentRepositories
        } else {
            currentRepositories.filter {
                it.name.contains(query, ignoreCase = true) ||
                        (it.description?.contains(query, ignoreCase = true) ?: false)
            }
        }
        notifyDataSetChanged()
    }

    fun onFavChanged(repository: Repository, isFavorite: Boolean) {
        if (isFavorite) {
            if (!currentRepositories.contains(repository)) {
                currentRepositories.add(repository)
                notifyItemInserted(currentRepositories.size - 1)
            }
        } else {
            val index = currentRepositories.indexOfFirst { it.id == repository.id }
            if (index != -1) {
                currentRepositories.removeAt(index)
                notifyItemRemoved(index)
            }
        }
    }

    fun onFavChangedIcon(repository: Repository, isFavorite: Boolean) {
        val index = currentRepositories.indexOfFirst { it.id == repository.id }
        if (index != -1) {
            notifyItemChanged(index)
        } else if (isFavorite) {
            notifyItemInserted(currentRepositories.size - 1)
        }
    }

    fun isEmpty(): Boolean {
        return filteredRepositories.isEmpty()
    }

    inner class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatar_image_view)
        private val nameTextView: TextView = itemView.findViewById(R.id.name_text_view)
        private var descriptionTextView: TextView =
            itemView.findViewById(R.id.description_text_view)
        private val starsTextView: TextView = itemView.findViewById(R.id.stars_text_view)
        private val favoriteImageView: ImageView = itemView.findViewById(R.id.favorite_image_view)

        fun bind(repository: Repository) {
            nameTextView.text = "${repository.owner.login}/${repository.name}"
            descriptionTextView.text = repository.description ?: "No description available"
            starsTextView.text = formatCount(repository.stargazers_count)
            Glide.with(itemView.context)
                .load(repository.owner.avatar_url)
                .placeholder(R.drawable.ic_no_avatar)
                .error(R.drawable.ic_no_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(avatarImageView)

            val favorites = favoriteRepositoryManager.getFavorites().toMutableList()
            favoriteImageView.setImageResource(
                if (favorites.any { it.id == repository.id }) R.drawable.ic_fr
                else R.drawable.ic_fb
            )

            itemView.setOnClickListener {
                RepositoryDetailActivity.start(itemView.context, repository)
            }

            favoriteImageView.setOnClickListener {
                if (favorites.any { it.id == repository.id }) {
                    favoriteRepositoryManager.removeFavorite(repository)
                    favoriteImageView.setImageResource(R.drawable.ic_fb) // Not favorite
                    EventBus.getDefault().post(FavoriteRepositoryEvent(repository, false))
                } else {
                    favoriteRepositoryManager.addFavorite(repository)
                    favoriteImageView.setImageResource(R.drawable.ic_fr) // Favorite
                    EventBus.getDefault().post(FavoriteRepositoryEvent(repository, true))
                }
            }
        }
    }
}

fun formatCount(count: Int): String {
    return when {
        count >= 1_000_000 -> "${count / 1_000_000}M"  // Millions
        count >= 1_000 -> "${count / 1_000}K"          // Thousands
        else -> count.toString()
    }
}

class RepositoryDiffCallback : DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }
}
package com.neu.trend.features.detials

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.neu.trend.R
import com.neu.trend.databinding.ActivityRepositoryDetailBinding
import com.neu.trend.domain.model.response.githubrepo.Repository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRepositoryDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backArrow.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val repository = intent.getSerializableExtra("repository") as? Repository
        repository?.let { setupRepositoryDetails(it) }
    }

    private fun setupRepositoryDetails(repository: Repository) = with(binding) {
        Glide.with(this@RepositoryDetailActivity)
            .load(repository.owner.avatar_url)
            .placeholder(R.drawable.ic_no_avatar)
            .error(R.drawable.ic_no_avatar)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(avatarImageView)

        nameTextView.text = repository.name
        descriptionTextView.text = repository.description ?: getString(R.string.no_description)
        languageTextView.text = repository.language ?: getString(R.string.unknown_language)
        forksTextView.text = repository.forks.toString()
        createdAtTextView.text = repository.created_at

        setupHtmlUrl(repository.html_url)
    }

    private fun setupHtmlUrl(url: String) = with(binding.htmlUrlTextView) {
        text = url
        setTextColor(ContextCompat.getColor(context, R.color.blue))
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
        setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    companion object {
        fun start(context: Context, repository: Repository) {
            val intent = Intent(context, RepositoryDetailActivity::class.java).apply {
                putExtra("repository", repository)
            }
            context.startActivity(intent)
        }
    }
}

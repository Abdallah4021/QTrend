package com.neu.trend.features.fav

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neu.trend.R
import com.neu.trend.databinding.FragmentFavBinding
import com.neu.trend.db.FavoriteRepositoryManager
import com.neu.trend.features.base.BaseFragment
import com.neu.trend.features.trending.adapter.RepositoryAdapter
import com.neu.trend.features.trending.model.FavoriteRepositoryEvent
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class FavFragment : BaseFragment(R.layout.fragment_fav) {


    private val viewModel: FavViewModel by viewModels()
    private val binding by viewBinding(FragmentFavBinding::bind)
    private lateinit var adapter: RepositoryAdapter
    private lateinit var favoriteRepositoryManager: FavoriteRepositoryManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
        checkEmptyState()
    }

    private fun checkEmptyState() {
        if (adapter.isEmpty()) {
            binding.lottieAnimationView.visibility = View.VISIBLE
            binding.lottieAnimationView.playAnimation()
            binding.searchEditText.visibility = View.GONE
        } else {
            binding.lottieAnimationView.visibility = View.GONE
            binding.searchEditText.visibility = View.VISIBLE
            binding.lottieAnimationView.cancelAnimation()
        }
    }

    private fun setupListeners() {
        binding.searchEditText.addTextChangedListener { text ->
            if (text.isNullOrEmpty()) {
                adapter.filter("")
            } else {
                adapter.filter(text.toString())
            }
        }
    }


    private fun setupViews() {
        favoriteRepositoryManager = FavoriteRepositoryManager(requireContext())
        adapter = RepositoryAdapter(context = requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        // Retrieve favorite repositories and update the adapter
        val favoriteRepositories = favoriteRepositoryManager.getFavorites()
        adapter.updateRepositories(favoriteRepositories)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: FavoriteRepositoryEvent) {
        adapter.onFavChanged(event.repository, event.isFavorite)
        checkEmptyState()

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


    companion object {
        @JvmStatic
        fun newInstance() = FavFragment()
    }
}
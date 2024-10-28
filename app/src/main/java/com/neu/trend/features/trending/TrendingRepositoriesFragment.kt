package com.neu.trend.features.trending

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.neu.trend.R
import com.neu.trend.databinding.FragmentTrendingRepositoriesBinding
import com.neu.trend.features.base.BaseFragment
import com.neu.trend.features.trending.adapter.RepositoryAdapter
import com.neu.trend.features.trending.model.FavoriteRepositoryEvent
import com.neu.trend.main.MainActivity
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class TrendingRepositoriesFragment : BaseFragment(R.layout.fragment_trending_repositories) {

    private val viewModel: TrendingRepositoriesViewModel by viewModels()
    private val binding by viewBinding(FragmentTrendingRepositoriesBinding::bind)
    private lateinit var adapter: RepositoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
        observerLiveData()
    }

    private fun observerLiveData() {
        viewModel.loadTrendingRepositories("created:>2023-01-01")
        viewModel.repositories.observe(viewLifecycleOwner) { repositories ->
            adapter.updateRepositories(repositories)
            binding.searchEditText.visibility = View.VISIBLE
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.lottieAnimationView.visibility = View.VISIBLE
                binding.lottieAnimationView.playAnimation()
            } else {
                binding.lottieAnimationView.visibility = View.GONE
                binding.lottieAnimationView.cancelAnimation()
            }
        }
    }

    private fun setupListeners() {
        binding.searchEditText.addTextChangedListener { text ->
            if (text.isNullOrEmpty()) {
                viewModel.stopSearchMode()
                adapter.filter("")
            } else {
                viewModel.startSearchMode()
                adapter.filter(text.toString())

            }
        }
        binding.btnRetry.setOnClickListener {
            if ((activity as MainActivity).hasInternetConnection()) {
                viewModel.retryLoadRepositories()
                binding.lottieAnimationViewInternet.visibility = View.GONE
                binding.btnRetry.visibility = View.GONE
            } else {
                showNoInternetSnackbar()
            }
        }
    }

    // TrendingRepositoriesFragment.kt
    private fun setupViews() {
        if (!(activity as MainActivity).hasInternetConnection()) {
            binding.lottieAnimationViewInternet.visibility = View.VISIBLE
            binding.lottieAnimationViewInternet.playAnimation()
            binding.btnRetry.visibility = View.VISIBLE
        }

        adapter = RepositoryAdapter(context = requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    if ((activity as MainActivity).hasInternetConnection()) {
                        // Check if not loading and we are near the bottom of the list
                        if (viewModel.isLoading.value == false && (lastVisibleItem + 5 >= totalItemCount)) {
                            viewModel.loadMoreRepositories("month")
                        }
                    } else {
                        showNoInternetSnackbar()
                    }
                }
            }
        })
    }

    private fun showNoInternetSnackbar() {
        Snackbar.make(requireView(), "No internet connection", Snackbar.LENGTH_SHORT).show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: FavoriteRepositoryEvent) {
        adapter.onFavChangedIcon(event.repository, event.isFavorite)
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
        fun newInstance() = TrendingRepositoriesFragment()
    }
}
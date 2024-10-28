package com.neu.trend.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.neu.trend.R
import com.neu.trend.databinding.ActivityMainBinding
import com.neu.trend.extensions.viewBinding
import com.neu.trend.features.base.BaseActivity
import com.neu.trend.features.fav.FavFragment
import com.neu.trend.features.trending.TrendingRepositoriesFragment
import dagger.hilt.android.AndroidEntryPoint

const val EXTRA_CURRENT_POSITION = "EXTRA_CURRENT_POSITION"

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    // ViewModel

    // View binding
    private val binding by viewBinding(ActivityMainBinding::inflate)

    // Fragments
    private lateinit var trendingFragment: TrendingRepositoriesFragment
    private lateinit var favFragment: FavFragment
    private var activeFragment: Fragment? = null

    // State
    private var position = 0

    // Task list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        if (savedInstanceState == null) {
            setupFragments()
        } else {
            restoreFragments(savedInstanceState)
        }

        setupView()
        setupNavigationBar()
    }

    private fun setupFragments() {
        trendingFragment = TrendingRepositoriesFragment.newInstance()
        favFragment = FavFragment.newInstance()

        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.fragmentContainerView,
                trendingFragment,
                TrendingRepositoriesFragment::class.java.simpleName
            ).hide(trendingFragment)
            add(R.id.fragmentContainerView, favFragment, FavFragment::class.java.simpleName).hide(
                favFragment
            )
            commit()
        }

        showFragment(trendingFragment)
    }

    private fun restoreFragments(savedInstanceState: Bundle?) {
        val fragmentList: List<Fragment> = supportFragmentManager.fragments
        for (fragment in fragmentList) {
            when (fragment) {
                is TrendingRepositoriesFragment -> trendingFragment = fragment
                is FavFragment -> favFragment = fragment
            }
        }

        position = savedInstanceState?.getInt(EXTRA_CURRENT_POSITION) ?: 0
        activeFragment = if (position == 0) trendingFragment else favFragment
        showFragment(activeFragment!!)
    }

    private fun setupNavigationBar() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_trending -> {
                    showFragment(trendingFragment)
                    true
                }
                R.id.nav_favorite -> {
                    showFragment(favFragment)
                    true
                }
                else -> {
                    // Default
                    showFragment(trendingFragment)
                    true
                }
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        if (fragment == activeFragment) return

        supportFragmentManager.beginTransaction().apply {
            activeFragment?.let { hide(it) }
            show(fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            commit()
        }
        activeFragment = fragment
    }

    private fun setupView() {
        binding.rvTask.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(EXTRA_CURRENT_POSITION, position)
    }

    companion object {


        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
        }

    }
}
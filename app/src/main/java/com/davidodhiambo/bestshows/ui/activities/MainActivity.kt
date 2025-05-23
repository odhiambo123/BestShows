package com.davidodhiambo.bestshows.ui.activities

package com.davidodhiambo.bestshows.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.davidodhiambo.bestshows.data.model.Show
import com.davidodhiambo.bestshows.databinding.ActivityMainBinding
import com.davidodhiambo.bestshows.ui.detail.DetailActivity
import com.davidodhiambo.bestshows.util.Constants.Companion.SHOWS_ID
import com.davidodhiambo.bestshows.util.Resource // Added import
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val showsViewModel: ShowsViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var showAdapter: ShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showAdapter = ShowAdapter()

        setUpRecyclerView()
        observeShows() // Call observeShows to set up the observer
        getShows() // Then fetch shows
        setOnCardViewClick()

    }

    private fun setUpRecyclerView() {
        binding.mRecyclerView.apply {
            adapter = showAdapter
        }
    }

    private fun getShows() {
        showsViewModel.getShows()
    }

    private fun observeShows() {
        showsViewModel.shows.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.mProgressBar.visibility = View.VISIBLE
                    binding.mRecyclerView.visibility = View.GONE
                    binding.tvErrorMessageMain.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.mProgressBar.visibility = View.GONE
                    binding.tvErrorMessageMain.visibility = View.GONE
                    resource.data?.let { showsList ->
                        showAdapter.setShows(showsList)
                        binding.mRecyclerView.visibility = View.VISIBLE
                    } ?: run {
                        // Handle null data in success case
                        binding.tvErrorMessageMain.text = "No shows found."
                        binding.tvErrorMessageMain.visibility = View.VISIBLE
                        binding.mRecyclerView.visibility = View.GONE
                    }
                }
                is Resource.Error -> {
                    binding.mProgressBar.visibility = View.GONE
                    binding.mRecyclerView.visibility = View.GONE
                    binding.tvErrorMessageMain.text = resource.message ?: "An unknown error occurred."
                    binding.tvErrorMessageMain.visibility = View.VISIBLE
                    Log.e("MainActivity", "Error: ${resource.message}")
                }
            }
        }
    }

    private fun setOnCardViewClick() {
        showAdapter.setOnCardViewClick(object : ShowAdapter.OnCardViewClick {
            override fun onClick(show: Show) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(SHOWS_ID, show.id)
                startActivity(intent)
            }
        })
    }
}

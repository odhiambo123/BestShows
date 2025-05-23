package com.davidodhiambo.bestshows.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import coil.load
import com.davidodhiambo.bestshows.R
import com.davidodhiambo.bestshows.data.model.Show
import com.davidodhiambo.bestshows.databinding.ActivityDetailBinding
import com.davidodhiambo.bestshows.util.Constants.Companion.SHOWS_ID
import com.davidodhiambo.bestshows.util.Resource // Added import
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding
    private lateinit var show: Show // This will be set on Resource.Success
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIdFromIntent()
        observeShow() // Call observeShow to set up the observer
        getShowById() // Then fetch the show details
    }

    private fun getIdFromIntent() {
        val intent = intent
        id = intent.getIntExtra(SHOWS_ID, 0)
    }

    private fun getShowById() {
        detailViewModel.getShowById(id)
    }

    private fun observeShow() {
        detailViewModel.show.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.mProgressBar.visibility = View.VISIBLE
                    binding.mScrollView.visibility = View.GONE
                    binding.tvErrorMessageDetail.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.mProgressBar.visibility = View.GONE
                    binding.tvErrorMessageDetail.visibility = View.GONE
                    resource.data?.let { showData ->
                        this.show = showData // Assign the show data
                        setViews()
                        binding.mScrollView.visibility = View.VISIBLE
                    } ?: run {
                        // Handle null data in success case
                        binding.tvErrorMessageDetail.text = "Show details not found."
                        binding.tvErrorMessageDetail.visibility = View.VISIBLE
                        binding.mScrollView.visibility = View.GONE
                    }
                }
                is Resource.Error -> {
                    binding.mProgressBar.visibility = View.GONE
                    binding.mScrollView.visibility = View.GONE
                    binding.tvErrorMessageDetail.text = resource.message ?: "An unknown error occurred."
                    binding.tvErrorMessageDetail.visibility = View.VISIBLE
                    Log.e("DetailActivity", "Error: ${resource.message}")
                }
            }
        }
    }

    private fun setViews() {
        binding.mId.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.id) + "</b>" + " " + show.id, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.mRating.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.rating) + "</b>" + " " + show.rating?.average, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.mName.text = show.name

        binding.mImage.load(show.image?.original) {
            placeholder(R.color.purple_200)
            error(R.color.purple_200)
            crossfade(true)
            crossfade(400)
        }

        if (show.summary != null) {
            binding.mSummary.text = HtmlCompat.fromHtml(show.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            binding.mSummary.text = "Summary not available." // Placeholder text
        }

        if (show.network?.name == null) {
            binding.mNetwork.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.network) + "</b>" + " " + resources.getString(R.string.no_network), HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            binding.mNetwork.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.network) + "</b>" + " " + show.network?.name, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        if (show.schedule?.time == null) {
            binding.mSchedule.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.schedule) + "</b>" + " " + show.schedule?.days, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            binding.mSchedule.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.schedule) + "</b>" + " " + show.schedule?.days + " " + resources.getString(R.string.at) + " " + show.schedule?.time, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        if (show.status == null) {
            binding.mStatus.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.status) + "</b>" + " " + resources.getString(R.string.no_status), HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            binding.mStatus.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.status) + "</b>" + " " + show.status, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        if (show.type == null) {
            binding.mType.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.type) + "</b>" + " " + resources.getString(R.string.no_type), HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            binding.mType.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.type) + "</b>" + " " + show.type, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        val genresList = show.genres
        val genresText = if (!genresList.isNullOrEmpty()) {
            when (genresList.size) {
                1 -> "<b>" + resources.getString(R.string.genres) + "</b>" + " " + genresList[0]
                2 -> "<b>" + resources.getString(R.string.genres) + "</b>" + " " + genresList[0] + " | " + genresList[1]
                3 -> "<b>" + resources.getString(R.string.genres) + "</b>" + " " + genresList[0] + " | " + genresList[1] + " | " + genresList[2]
                // For 4 or more, join them all. This handles the case of 4 as well.
                else -> "<b>" + resources.getString(R.string.genres) + "</b>" + " " + genresList.joinToString(" | ")
            }
        } else {
            "<b>" + resources.getString(R.string.genres) + "</b>" + " " + resources.getString(R.string.no_genres)
        }
        binding.mGenres.text = HtmlCompat.fromHtml(genresText, HtmlCompat.FROM_HTML_MODE_LEGACY)

        if (show.officialSite == null) {
            binding.mOfficialSite.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.official_site) + "</b>" + " " + resources.getString(R.string.no_official_site), HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            binding.mOfficialSite.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.official_site) + "</b>" + " " + show.officialSite, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }
}
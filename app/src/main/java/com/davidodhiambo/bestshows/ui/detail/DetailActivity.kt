package com.davidodhiambo.bestshows.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import coil.load
import com.davidodhiambo.bestshows.R
import com.davidodhiambo.bestshows.data.model.Show
import com.davidodhiambo.bestshows.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding
    private lateinit var show: Show
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIdFromIntent()
        getShowById()
        observeShow()
        disableViews()

    }

    private fun getIdFromIntent() {

        val intent = intent
        id = intent.getIntExtra("id", 0)

    }

    private fun getShowById() {

        detailViewModel.getShowById(id)

    }

    private fun observeShow() {

        detailViewModel.observeShow().observe(this) {

            show = it
            setViews()
            enableViews()

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

        binding.mSummary.text = HtmlCompat.fromHtml(show.summary!!, HtmlCompat.FROM_HTML_MODE_LEGACY)

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

        when (show.genres!!.size) {

            1 -> binding.mGenres.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.genres) + "</b>" + " " + show.genres!![0], HtmlCompat.FROM_HTML_MODE_LEGACY)

            2 -> binding.mGenres.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.genres) + "</b>" + " " + show.genres!![0] + " | " + show.genres!![1], HtmlCompat.FROM_HTML_MODE_LEGACY)

            3 -> binding.mGenres.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.genres) + "</b>" + " " + show.genres!![0] + " | " + show.genres!![1] + " | " + show.genres!![2], HtmlCompat.FROM_HTML_MODE_LEGACY)

            4 -> binding.mGenres.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.genres) + "</b>" + " " + show.genres!![0] + " | " + show.genres!![1] + " | " + show.genres!![2] + " | " + show.genres!![3], HtmlCompat.FROM_HTML_MODE_LEGACY)

            else -> binding.mGenres.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.genres) + "</b>" + " " + resources.getString(R.string.no_genres), HtmlCompat.FROM_HTML_MODE_LEGACY)

        }

        if (show.officialSite == null) {
            binding.mOfficialSite.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.official_site) + "</b>" + " " + resources.getString(R.string.no_official_site), HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            binding.mOfficialSite.text = HtmlCompat.fromHtml("<b>" + resources.getString(R.string.official_site) + "</b>" + " " + show.officialSite, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }



    }

    private fun enableViews() {

        binding.mScrollView.visibility = View.VISIBLE
        binding.mProgressBar.visibility = View.GONE

    }

    private fun disableViews() {

        binding.mScrollView.visibility = View.GONE
        binding.mProgressBar.visibility = View.VISIBLE

    }

}
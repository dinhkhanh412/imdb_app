package com.koro.movies.presenter.ui.detail

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.bundle.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.koro.movies.databinding.FragmentMovieDetailBinding
import com.koro.movies.domain.image.ImageLoader
import com.koro.movies.presenter.adapter.GenreAdapter
import com.koro.movies.presenter.adapter.MovieInfoAdapter
import com.koro.movies.presenter.adapter.ProductionCompanyAdapter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue


class MovieDetailFragment : Fragment() {
    private val viewModel: MovieDetailViewModel by viewModel<MovieDetailViewModel>()
    private val imageLoader: ImageLoader by inject()

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var movieInfoAdapter: MovieInfoAdapter
    private lateinit var productionCompanyAdapter: ProductionCompanyAdapter

    val navArgs by navArgs<MovieDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeData()
        viewModel.getMovieDetail(navArgs.movieId)
    }

    private fun initView() {
        setUpGenreAdapter()
        setUpMovieInfoAdapter()
        setUpProductionCompanyAdapter()
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieDetail
                .filterNotNull()
                .collect { movie ->
                    binding.apply {
                        tvMovieName.text = movie.title
                        tvTitle.text = movie.title
                        tvOverview.text = movie.overview
                        tvYearRuntime.text = "${movie.releaseDate}    ${movie.runtime}m"
                        imageLoader.load(movie.posterPath.orEmpty(), ivPoster)
                        imageLoader.load(movie.backdropPath.orEmpty(), ivBackDrop)
                        genreAdapter.submitList(movie.genres)
                        movieInfoAdapter.submitList(movie.movieDetailInfos)
                        productionCompanyAdapter.submitList(movie.productionCompanies)
                    }

                    movie.imdbId?.let {
                        setUpImdb(it)
                    }

                    movie.homepage?.let {
                        setUpHome(it)
                    }
                }
        }
    }

    private fun setUpGenreAdapter() {
        genreAdapter = GenreAdapter()
        binding.rvGenres.apply {
            adapter = genreAdapter
        }
    }

    private fun setUpMovieInfoAdapter() {
        movieInfoAdapter = MovieInfoAdapter()
        binding.rvMovieInfo.apply {
            adapter = movieInfoAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setUpProductionCompanyAdapter() {
        productionCompanyAdapter = ProductionCompanyAdapter(imageLoader)
        binding.rvProduction.apply {
            adapter = productionCompanyAdapter
        }
    }

    private fun setUpImdb(imdbId: String) {
        binding.btnImdb.isVisible = true
        binding.btnImdb.setOnClickListener {
            imdbId.let {
                val url = "https://www.imdb.com/title/$it"
                openExternalLink(url)
            }
        }
    }

    private fun setUpHome(homeUrl: String) {
        binding.btnHomePage.isVisible = true
        binding.btnHomePage.setOnClickListener {
            openExternalLink(homeUrl)
        }
    }

    private fun openExternalLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}

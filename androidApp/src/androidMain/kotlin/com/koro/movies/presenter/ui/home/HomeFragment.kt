package com.koro.movies.presenter.ui.home

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.bundle.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.koro.movies.R
import com.koro.movies.databinding.FragmentHomeBinding
import com.koro.movies.domain.image.ImageLoader
import com.koro.movies.presenter.adapter.MovieAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModel<HomeViewModel>()
    private val imageLoader: ImageLoader by inject()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchBox()
        observeData()
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(
            imageLoader = imageLoader, onItemClick = { movie ->
                navigateToDetail(movie.id)
            })
        binding.rvTrendingMovies.apply {
            adapter = movieAdapter
            addItemDecoration(
                DividerItemDecoration(context, RecyclerView.VERTICAL).apply {
                    setDrawable(ContextCompat.getDrawable(context, R.drawable.item_divider)!!)
                })
        }
    }

    private fun setupSearchBox() {
        binding.etSearch.apply {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.searchMovie(this.text.toString())
                    clearFocusAndHideKeyboard()
                }
                true
            }

            binding.etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s.isNullOrEmpty()) {
                        viewModel.loadLocalTrendingMovie()
                        clearFocusAndHideKeyboard()
                    }
                }
            })
        }
    }


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.homeUiState.collect { state ->
                    when (state) {
                        is HomeUiState.Loading -> {
                            binding.tvLoading.isVisible = true
                        }

                        is HomeUiState.ShowTrendingMovies -> {
                            binding.tvLoading.isVisible = false
                            binding.tvMovieType.text = "Trending Movies"
                            movieAdapter.submitList(state.movies)
                        }

                        is HomeUiState.ShowSearchMovies -> {
                            binding.tvLoading.isVisible = false
                            binding.tvMovieType.text = "Search Results"
                            movieAdapter.submitList(state.movies)
                        }

                        is HomeUiState.Error -> {
                            binding.tvLoading.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun navigateToDetail(movieId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId = movieId)
        findNavController().navigate(action)
    }

    private fun clearFocusAndHideKeyboard() {
        binding.etSearch.clearFocus()
        val ime = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        ime.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }
}

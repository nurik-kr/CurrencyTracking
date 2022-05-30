package com.example.currencytracking.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.currencytracking.R
import com.example.currencytracking.databinding.FragmentFavoritesBinding
import com.example.currencytracking.utils.ErrorHandler
import com.example.currencytracking.utils.bindingLifecycleError
import com.example.currencytracking.utils.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding ?: bindingLifecycleError()

    private lateinit var adapter: FavoritesCurrenciesAdapter

    private val favoritesViewModel by viewModel(FavoritesViewModel::class)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFavoritesCurrency()
        setupRefresh()
        emptyData()
        setupClickListener()
    }

    private fun getFavoritesCurrency() {
        val onDeleteClicked: (currency: String) -> Unit = { currency ->
            favoritesViewModel.changeFavoriteCurrencies(currency)
        }

        adapter = FavoritesCurrenciesAdapter(
            onDeleteClicked = onDeleteClicked
        )

        binding.recyclerview.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            favoritesViewModel.rates.collect {
                adapter.setRates(it)
            }
        }
    }

    private fun setupRefresh() {
        viewLifecycleOwner.lifecycleScope.launch {
            favoritesViewModel.refreshStatus.collect {
                binding.swipeRefresh.isRefreshing = it
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            favoritesViewModel.getAllFavoriteCurrenciesRates()
        }
    }

    private fun emptyData() {
        viewLifecycleOwner.lifecycleScope.launch {
            favoritesViewModel.contentIsEmpty.collect {
                binding.hintText.text = context?.resources?.getString(R.string.text_hint) ?: ""
                binding.hintText.isVisible = !it
                binding.deleteAllButton.isVisible = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            favoritesViewModel.error.collect {
                ErrorHandler.showAlertDialog(
                    context = requireContext(),
                    message = it,
                    onPositiveButtonClicked = { favoritesViewModel.getAllFavoriteCurrenciesRates() }
                )
            }
        }
    }

    private fun setupClickListener() {
        binding.deleteAllButton.setOnClickListener {
            favoritesViewModel.deleteAllFavoriteCurrencies()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
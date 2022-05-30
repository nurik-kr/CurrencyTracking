package com.example.currencytracking.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.currencytracking.databinding.FragmentPopularBinding
import com.example.currencytracking.utils.ErrorHandler
import com.example.currencytracking.utils.Utility.getAllCountries
import com.example.currencytracking.utils.Utility.getCountryCode
import com.example.currencytracking.utils.Utility.getSymbol
import com.example.currencytracking.utils.Utility.hideKeyboard
import com.example.currencytracking.utils.bindingLifecycleError
import com.example.currencytracking.utils.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding ?: bindingLifecycleError()

    private lateinit var adapter: RatesAdapter

    private val popularViewModel by viewModel(PopularViewModel::class)

    private var selectedItem1: String? = "USD"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()
        setupRv()
        getPopularCurrency()
    }

    private fun setupSpinner() {
        binding.spnCurrency.setItems( getAllCountries() )
        binding.spnCurrency.setOnClickListener {
            hideKeyboard(requireActivity())
        }

        binding.spnCurrency.setOnItemSelectedListener { _, _, _, item ->
            val countryCode = getCountryCode(item.toString())
            val currencySymbol = getSymbol(countryCode)
            selectedItem1 = currencySymbol
            binding.valutaName.text = "Валюта страны: $selectedItem1"
        }
    }

    private fun setupRv() {
        val onStarClicked: (currency: String) -> Unit = { currency ->
            popularViewModel.changeFavoriteCurrencies(currency)
        }
        adapter = RatesAdapter(onStarClicked = onStarClicked)
        binding.rvPopular.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            popularViewModel.rates.collect {
                adapter.setRates(it)
            }
        }
    }

    private fun getPopularCurrency() {
        viewLifecycleOwner.lifecycleScope.launch {
            popularViewModel.refreshStatus.collect {
                binding.swipeRefresh.isRefreshing = it
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            popularViewModel.loadData(selectedItem1)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            popularViewModel.error.collect {
                ErrorHandler.showAlertDialog(
                    context = requireContext(),
                    message = it,
                    onPositiveButtonClicked = { popularViewModel.loadData(selectedItem1) }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        popularViewModel.loadData(selectedItem1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
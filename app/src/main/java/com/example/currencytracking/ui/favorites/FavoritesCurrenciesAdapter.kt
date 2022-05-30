package com.example.currencytracking.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencytracking.data.viewobjects.RateVo
import com.example.currencytracking.data.viewobjects.RatesVo
import com.example.currencytracking.databinding.ItemRvFavoritesBinding

class FavoritesCurrenciesAdapter(
    private val onDeleteClicked: (currency: String) -> Unit,
) : RecyclerView.Adapter<FavoritesCurrenciesViewHolder>() {

    private lateinit var binding: ItemRvFavoritesBinding

    var rates = mutableListOf<RateVo>()

    @SuppressLint("NotifyDataSetChanged")
    fun setRates(ratesResponse: RatesVo) {
        this.rates = ratesResponse.rates.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesCurrenciesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRvFavoritesBinding.inflate(inflater, parent, false)
        val viewHolder = FavoritesCurrenciesViewHolder(binding)
        binding.icDelete.setOnClickListener {
            if (viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                onDeleteClicked.invoke(rates[viewHolder.adapterPosition].currency)
                rates.removeAt(viewHolder.adapterPosition)
                notifyItemRemoved(viewHolder.adapterPosition)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FavoritesCurrenciesViewHolder, position: Int) {
        val rate = rates[position]
        holder.bind(rate, binding)
    }

    override fun getItemCount(): Int {
        return rates.size
    }
}

class FavoritesCurrenciesViewHolder(binding: ItemRvFavoritesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(rate: RateVo, binding: ItemRvFavoritesBinding) {
        binding.currencyName.text = "Валюта : " + rate.currency
        binding.currencyValue.text = rate.value.toString()
    }

}
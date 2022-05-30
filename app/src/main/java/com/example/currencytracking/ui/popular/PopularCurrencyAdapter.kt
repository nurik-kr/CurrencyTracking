package com.example.currencytracking.ui.popular

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencytracking.R
import com.example.currencytracking.data.viewobjects.RateVo
import com.example.currencytracking.data.viewobjects.RatesVo
import com.example.currencytracking.databinding.AdapterRateBinding
import com.example.currencytracking.databinding.ItemRvBinding

class RatesAdapter(
    private val onStarClicked: (currency: String) -> Unit,
) : RecyclerView.Adapter<RateViewHolder>() {

    private lateinit var binding: AdapterRateBinding

    var rates = listOf<RateVo>()

    @SuppressLint("NotifyDataSetChanged")
    fun setRates(ratesResponse: RatesVo) {
        this.rates = ratesResponse.rates
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = AdapterRateBinding.inflate(inflater, parent, false)
        val viewHolder = RateViewHolder(binding)
        viewHolder.favorite.setOnClickListener {
            if (viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                onStarClicked.invoke(rates[viewHolder.adapterPosition].currency)
                viewHolder.changeStar()
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rate = rates[position]
        holder.bind(rate, binding)
    }

    override fun getItemCount(): Int {
        return rates.size
    }
}

class RateViewHolder(binding: AdapterRateBinding) : RecyclerView.ViewHolder(binding.root) {
    private var isFavorite = false

    private val currency = binding.currency
    private val value = binding.value
    val favorite = binding.favorite

    fun bind(rate: RateVo, binding: AdapterRateBinding) {
        binding.currency.text = rate.currency
        binding.value.text = rate.value.toString()
        isFavorite = !rate.favorite
        changeStar()
    }

    fun changeStar() {
        isFavorite = isFavorite.not()
        val imageDrawable = if (isFavorite) {
            R.drawable.ic_star_filled
        } else {
            R.drawable.ic_star_empty
        }
        favorite.setImageResource(imageDrawable)
    }
}
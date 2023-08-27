package com.example.entropy_calculation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.entropy_calculation.databinding.ItemBinding

class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    val binding = ItemBinding.bind(itemView)
    fun bind(element : Probability){
        if(element.bigram == null) {
            binding.tvChar.text = element.chars
            binding.tvProbability.text = element.probabilChar.toString()
        }
        else{
            binding.tvChar.text = element.bigram
            binding.tvProbability.text = element.probabilBigram.toString()
        }
    }
}
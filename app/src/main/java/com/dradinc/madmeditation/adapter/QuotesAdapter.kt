package com.dradinc.madmeditation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dradinc.madmeditation.R
import com.dradinc.madmeditation.common.Global
import com.dradinc.madmeditation.databinding.QuotesItemBinding
import com.dradinc.madmeditation.model.Quotes
import com.squareup.picasso.Picasso

class QuotesAdapter (val listner: Listner) : RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>() {

    class QuotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        // Биндинг в адаптере
        val binding = QuotesItemBinding.bind(itemView)
        fun bind(quotes: Quotes, listner: Listner) = with(binding){
            Picasso.get().load(quotes.image).into(image)
            title.text = quotes.title
            description.text = quotes.description
            // Прослушиватель нажатия на элемент
            quotesBtn.setOnClickListener {
                //
                listner.onClickQuotes(quotes)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        // Получаем наш макет для элемента списка
        return QuotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.quotes_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.bind(Global.quotesList[position], listner)
    }
    // Возвращаем длину списка
    override fun getItemCount() = Global.quotesList.size

    // Интерфейс для прослушивания нажатий
    interface Listner {
        //
        fun onClickQuotes(quotes: Quotes)
    }
}
package com.dradinc.madmeditation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dradinc.madmeditation.R
import com.dradinc.madmeditation.common.Global
import com.dradinc.madmeditation.databinding.MoodItemBinding
import com.dradinc.madmeditation.model.Mood
import com.squareup.picasso.Picasso

class MoodsAdapter (val listner: Listner) : RecyclerView.Adapter<MoodsAdapter.MoodsViewHolder>() {

    class MoodsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        // Биндинг в адаптере
        val binding = MoodItemBinding.bind(itemView)
        fun bind(mood: Mood, listner: Listner) = with(binding){
            Picasso.get().load(mood.image).into(moodIcon)
            moodTitle.text = mood.title
            // Прослушиватель нажатия на элемент
            itemView.setOnClickListener {
                //
                listner.onClickMood(mood)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodsViewHolder {
        // Получаем наш макет для элемента списка
        return MoodsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.mood_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: MoodsViewHolder, position: Int) {
        holder.bind(Global.moodsList[position], listner)
    }
    // Возвращаем длину списка
    override fun getItemCount() = Global.moodsList.size

    // Интерфейс для прослушивания нажатий
    interface Listner {
        //
        fun onClickMood(mood: Mood)
    }
}
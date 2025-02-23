package com.example.quote_of_the_day

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quote_of_the_day_app.R


class FavouriteQuotesAdapter(
    private val quotesList: List<FavouriteQuotesItems>,
    private val onDeleteClick: (FavouriteQuotesItems) -> Unit
) : RecyclerView.Adapter<FavouriteQuotesAdapter.QuoteViewHolder>() {

    inner class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val showQuoteText: TextView = itemView.findViewById(R.id.showQuote)
        val showAuthorText: TextView = itemView.findViewById(R.id.showAuthor)
        val deleteFavourite: ImageView = itemView.findViewById(R.id.deleteFavourite)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.favourites_items, parent, false)
        return QuoteViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return quotesList.size
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotesList[position]
        holder.showQuoteText.text = quote.quote
        holder.showAuthorText.text = quote.author
        holder.deleteFavourite.setOnClickListener {
            onDeleteClick(quote)
        }
    }

}

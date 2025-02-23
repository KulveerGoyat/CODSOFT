package com.example.quote_of_the_day

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quote_of_the_day_app.databinding.ActivityFavouriteQuoteBinding
import com.google.firebase.firestore.FirebaseFirestore

class FavouriteQuote : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteQuoteBinding
    private val db = FirebaseFirestore.getInstance()
    private val quotesList = mutableListOf<FavouriteQuotesItems>()
    private lateinit var adapter: FavouriteQuotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteQuoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
        displayQuotes()

        binding.backButton.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    private fun setRecyclerView() {
        adapter = FavouriteQuotesAdapter(quotesList) { quote ->
            deleteQuote(quote)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayQuotes() {
        db.collection("favourite_quotes")
            .get()
            .addOnSuccessListener { result ->
                quotesList.clear()
                for (document in result) {
                    val quote = document.toObject(FavouriteQuotesItems::class.java)
                    quotesList.add(quote)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load quotes: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteQuote(quote: FavouriteQuotesItems) {
        val quoteRef = db.collection("favourite_quotes")
            .whereEqualTo("quote", quote.quote)
            .whereEqualTo("author", quote.author)
            .get()

        quoteRef.addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                for (document in querySnapshot) {
                    document.reference.delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Quote Deleted", Toast.LENGTH_SHORT).show()
                            quotesList.remove(quote)
                            adapter.notifyDataSetChanged()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed To Delete Quote", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }
}

package com.example.quote_of_the_day

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quote_of_the_day_app.R
import com.example.quote_of_the_day_app.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getRandomQuote()

        binding.refreshButton.setOnClickListener{
            getRandomQuote()
            binding.favourite.setImageResource(R.drawable.heart2)
        }
        binding.shareButton.setOnClickListener {
            shareQuote()
        }
        binding.savedQuotesButton.setOnClickListener {
            startActivity(Intent(this,FavouriteQuote::class.java))
        }


        binding.favourite.setOnClickListener {
            if (binding.favourite.drawable.constantState == resources.getDrawable(R.drawable.heart).constantState) {
                binding.favourite.setImageResource(R.drawable.heart2)
            } else {
                binding.favourite.setImageResource(R.drawable.heart)
                val quote = FavouriteQuotesItems(binding.quoteText.text.toString(),binding.quoteTextAuthor.text.toString())
                saveQuote(quote)
            }
        }


    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getRandomQuote() {
        setProgressBar(true)
        GlobalScope.launch {
            try {
                val response = RetrofitClient.quoteService.getRandomQuote()
                runOnUiThread {
                    setProgressBar(false)
                    response.body()?.first()?.let {
                        displayQuote(it)
                    }
                }
            } catch (_: Exception) {
                runOnUiThread {
                    setProgressBar(false)
                    Toast.makeText(this@MainActivity, "Something Went Wrong.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayQuote(quote: Quote) {
        binding.quoteText.text = quote.q
        binding.quoteTextAuthor.text = "~" + quote.a
    }

    private fun setProgressBar(progress: Boolean) {
        if (progress) {
            binding.progressbar.visibility = View.VISIBLE
            binding.refreshButton.visibility = View.GONE
        } else {
            binding.progressbar.visibility = View.GONE
            binding.refreshButton.visibility = View.VISIBLE
        }

    }

    private fun shareQuote() {
        val quoteText = binding.quoteText.text.toString()
        if (quoteText.isBlank() || quoteText == "No quote available") {
            Toast.makeText(this, "No quote to share", Toast.LENGTH_SHORT).show()
            return
        }

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "💡 Quote of the Day:\n\n$quoteText\n\nShared via DailyQuote App 📖")
        }
        startActivity(Intent.createChooser(shareIntent, "Share Quote via"))
    }


    private  fun saveQuote(quote: FavouriteQuotesItems) {

        val quoteRef = db.collection("favourite_quotes").document()

        quoteRef.set(quote)
            .addOnSuccessListener {
                Toast.makeText(this, "Saved To Favourites.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { _ ->
                Toast.makeText(this, "Unable To Save.", Toast.LENGTH_SHORT).show()
            }
    }



}
